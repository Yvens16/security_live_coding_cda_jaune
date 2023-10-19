package com.example.security_live_coding;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Component
public class UserDetailsImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> userOptional = this.userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      UserEntity user = userOptional.get();


      List<GrantedAuthority> authorities = new ArrayList<>();


      String role = user.getRoles().get(0).getName();
      SimpleGrantedAuthority autority = new SimpleGrantedAuthority(role);
      authorities.add(autority);

      return User
      .withUsername(user.getUsername())
      .password("")
      .authorities(authorities)
      .build();
    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }

}
