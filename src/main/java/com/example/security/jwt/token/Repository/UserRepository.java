package com.example.security.jwt.token.Repository;

import com.example.security.jwt.token.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String UserName);
}
