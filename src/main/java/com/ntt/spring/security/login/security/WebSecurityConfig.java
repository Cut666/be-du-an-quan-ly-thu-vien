package com.ntt.spring.security.login.security;

import com.ntt.spring.security.login.security.jwt.AuthEntryPointJwt;
import com.ntt.spring.security.login.security.jwt.AuthTokenFilter;
import com.ntt.spring.security.login.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true)
public class WebSecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/api/auth/**", "view/signup","/view/signin", "/signin").permitAll()
            .antMatchers("**/updatelibrary").hasAnyRole("FREE","BUY")
            .antMatchers("**/customer/create").hasAnyRole("FREE","BUY")
            .antMatchers("**/customer/getAll").hasAnyRole("FREE","BUY")
            .antMatchers("**/customer/getAllSub").hasAnyRole("FREE","BUY")
            .antMatchers("**/customer/delete").hasAnyRole("FREE","BUY")
            .antMatchers("**/customer/updatecustomer").hasAnyRole("FREE","BUY")
            .antMatchers("**/customer/getByPhone").hasAnyRole("FREE","BUY")
            .antMatchers("**/supplier/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/product/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/cashfund/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/phieuchi/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/phieuthu/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/baoco/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/unc/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/export/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/receipt/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/WarehouseProduct/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/buy/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/sell/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/buysell/**").hasAnyRole("FREE","BUY")
            .antMatchers("**/rental/**").hasRole("BUY")
            .antMatchers("**/reRental/**").hasRole("BUY")
//            .antMatchers("**/payment/**").hasRole("FREE")
            .antMatchers("**/payment/**").hasAnyRole("FREE","BUY")
        .anyRequest().authenticated();

    http.headers().frameOptions().sameOrigin();
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}
