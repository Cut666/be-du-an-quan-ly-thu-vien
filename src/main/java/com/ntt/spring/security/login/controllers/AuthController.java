package com.ntt.spring.security.login.controllers;

import com.ntt.spring.security.login.exception.TokenRefreshException;
import com.ntt.spring.security.login.models.entity.RefreshToken;
import com.ntt.spring.security.login.models.fileenum.ERole;
import com.ntt.spring.security.login.models.entity.Role;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.payload.request.TokenRefreshRequest;
import com.ntt.spring.security.login.payload.response.JwtResponse;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.payload.response.TokenRefreshResponse;
import com.ntt.spring.security.login.repository.RoleRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.jwt.JwtUtils;
import com.ntt.spring.security.login.security.services.RefreshTokenService;
import com.ntt.spring.security.login.security.services.UserDetailsImpl;
import com.ntt.spring.security.login.payload.request.LoginRequest;
import com.ntt.spring.security.login.payload.request.SignupRequest;
import com.ntt.spring.security.login.security.services.itp.CashService;
import com.ntt.spring.security.login.security.services.itp.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  CashService cashService;
  @Autowired
  WalletService walletService;
  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
            .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
            userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Username đã tồn tại!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Email đã được sử dụng!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.FREE)
          .orElseThrow(() -> new RuntimeException("Lỗi: Role không tồn tại."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "buy":
          Role adminRole = roleRepository.findByName(ERole.BUY)
              .orElseThrow(() -> new RuntimeException("Lỗi: Role không tồn tại."));
          roles.add(adminRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.FREE)
              .orElseThrow(() -> new RuntimeException("Lỗi: Role không tồn tại."));
          roles.add(userRole);
        }
      });
    }
    user.setCash(cashService.createCash());
    user.setWallet(walletService.createWallet());
    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Bạn đã đăng ký người dùng thành công!"));
  }
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
              String token = jwtUtils.generateTokenFromUsername(user.getUsername());
              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                    "Refresh token is not in database!"));
  }
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }
}
