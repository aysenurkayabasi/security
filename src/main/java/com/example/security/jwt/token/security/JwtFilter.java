package com.example.security.jwt.token.security;

import com.example.security.jwt.token.service.JwtService;
import com.example.security.jwt.token.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String authHeader=request.getHeader("Authorization");
      String userName= null;
      String token=null;
      if(authHeader  != null && authHeader.startsWith("Bearer ")){
          token=authHeader.substring(7);
          userName=jwtService.extractUSer(token);
      }
      if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
          UserDetails user= userService.loadUserByUsername(userName);
          if(jwtService.validateToken(token,user)){
              UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }

      }
      filterChain.doFilter(request,response);
    }
}
