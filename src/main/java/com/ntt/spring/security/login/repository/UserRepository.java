package com.ntt.spring.security.login.repository;

import java.util.List;
import java.util.Optional;

import com.ntt.spring.security.login.models.entity.professionalKnowledge.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntt.spring.security.login.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  User findTopByOrderByIdDesc();

  User findByPhone(String phone);
}
