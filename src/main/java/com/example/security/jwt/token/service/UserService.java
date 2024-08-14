package com.example.security.jwt.token.service;

import com.example.security.jwt.token.Repository.UserRepository;
import com.example.security.jwt.token.dto.CreateUserRequest;
import com.example.security.jwt.token.model.User;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

    public User createUser(CreateUserRequest createUserRequest){
         User user=User.builder()
                 .name(createUserRequest.name())
                 .username(createUserRequest.username())
                 .password(passwordEncoder.encode(createUserRequest.password()))
                 .accountNonExpired(true)
                 .credentialsNonExpired(true)
                 .isEnabled(true)
                 .accountNonLocked(true)
                 .authorities(createUserRequest.authorities()).build();

         return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityExistsException::new);
    }
}
