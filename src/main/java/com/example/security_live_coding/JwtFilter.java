package com.example.security_live_coding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  JwtService jwtService;

  @Autowired
  UserDetailsImpl userDetailsImpl;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      String token = authorizationHeader.substring(7);

      if (token != null) {

        UserDetails user = this.userDetailsImpl.loadUserByUsername(this.jwtService.getSubject(token));

        if (this.jwtService.isValid(token, user.getUsername())) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              user.getUsername(),
              null,
              user.getAuthorities() // Roles qui permettent à ma config d'accepter la requête de l'utilisateur
          );

          System.out.println("@@@@@@@ credentials: " + usernamePasswordAuthenticationToken);

          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    }
    System.out.println("@@@@@@@ get to the next chain: ");
    filterChain.doFilter(request, response);
  }

}
