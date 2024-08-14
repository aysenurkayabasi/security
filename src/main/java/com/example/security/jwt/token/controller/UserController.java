package com.example.security.jwt.token.controller;

import com.example.security.jwt.token.dto.AuthRequest;
import com.example.security.jwt.token.dto.CreateUserRequest;
import com.example.security.jwt.token.model.User;
import com.example.security.jwt.token.service.JwtService;
import com.example.security.jwt.token.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

    private final UserService service;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "Hello World!";
    }


    @PostMapping("/addNewUser")
    public User welcome(@RequestBody CreateUserRequest request) {
        return service.createUser(request);
    }


    @GetMapping("/user")
    public String getUserString() {
        return "new User";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "new Admin";
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        }
        log.info("invalid username {} " + authRequest.username());
        throw new UsernameNotFoundException("invalid username {]" + authRequest.username());
    }


}
