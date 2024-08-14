package com.example.security.jwt.token.dto;

import com.example.security.jwt.token.model.Role;
import lombok.Builder;

import java.util.Set;


@Builder
public record CreateUserRequest (String username, String name, String password, Set<Role> authorities){
}
