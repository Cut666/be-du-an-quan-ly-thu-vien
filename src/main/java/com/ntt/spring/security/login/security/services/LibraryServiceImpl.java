package com.ntt.spring.security.login.security.services;


import com.ntt.spring.security.login.models.dto.LibraryDTO;
import com.ntt.spring.security.login.models.entity.Role;
import com.ntt.spring.security.login.models.entity.User;
import com.ntt.spring.security.login.models.entity.Wallet;
import com.ntt.spring.security.login.models.fileenum.ERole;
import com.ntt.spring.security.login.payload.response.MessageResponse;
import com.ntt.spring.security.login.repository.RoleRepository;
import com.ntt.spring.security.login.repository.UserRepository;
import com.ntt.spring.security.login.security.services.itp.CashService;
import com.ntt.spring.security.login.security.services.itp.LibraryService;
import com.ntt.spring.security.login.security.services.itp.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static com.ntt.spring.security.login.models.fileenum.ERole.BUY;

@Component
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    LibraryService libraryService;
    @Autowired
    UserRepository libraryRepository;
    @Autowired
    WalletService walletService;
    @Autowired
    CashService cashService;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Object getUser(long id){
        User user = libraryRepository.findById(id).get();
        return user;
    }

    @Override
    public Object updateWalletLibrary(LibraryDTO dto) {
        User library = libraryRepository.findById(dto.getId()).get();
        if (library == null){
            throw new RuntimeException("Chưa đăng nhập");
        }else {
            library.getWallet().setBalance(dto.getWallet().getBalance());
            libraryRepository.save(library);
            return new MessageResponse("Cập nhật thành công");
        }
    }

    @Override
    public Object updateRoleLibrary(Long id) {
        User user =libraryRepository.findById(id).get();
        Set<Role> roles = user.getRoles();

        // Tạo một đối tượng Role mới với giá trị "BUY"
        Role buyRole =roleRepository.findByName(BUY).orElseThrow();

        // Thay đổi vai trò của người dùng từ "FREE" sang "BUY"
        roles.removeIf(role -> role.getName().equals(ERole.FREE));
        roles.add(buyRole);

        // Cập nhật lại danh sách vai trò của người dùng trong cơ sở dữ liệu
        user.setRoles(roles);
        libraryRepository.save(user);
        return user;
    }

    @Override
    public Object updateLibrary(LibraryDTO dto) {
        User library = libraryRepository.findById(dto.getId()).get();
            library.setTax(dto.getTax());
            library.setName(dto.getName());
            library.setPhone(dto.getPhone());
            library.setAddress(dto.getAddress());
            libraryRepository.save(library);
            return new MessageResponse("Cập nhật thành công");
    }

    @Override
    public Object deleteLibrary(long id) {
        User library = libraryRepository.findById(id).get();
        if (library == null){
            throw new RuntimeException("Chưa đăng nhập");
        }else {
            libraryRepository.delete(library);
            return new MessageResponse("Xóa thành công");
        }
    }
}
