package com.example.security_live_coding;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  // .claim("hello", "world")

  public SecretKey generateKey() {
    byte[] secretByte = Decoders.BASE64.decode("fiudhdhbuezbfudbuibnkubzeiubku5465678782U92JJGHHGJBVGT27687783U9J");
    SecretKey key = Keys.hmacShaKeyFor(secretByte);
    return key;
  }

  public String generateToken(String username, String role) {
    String jwt = Jwts.builder()
        .subject(username)
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
        .claim("role", role)
        // .claim("age", "age à implementer")
        .signWith(generateKey())
        .compact();
    return jwt;
  }

  public String getSubject(String token) {
    String subject = Jwts
        .parser()
        .verifyWith(generateKey())
        .build().parseSignedClaims(token)
        .getPayload().getSubject();
    return subject;
  }

  public String getRole(String token) {
    String role = Jwts
        .parser()
        .verifyWith(generateKey())
        .build().parseSignedClaims(token)
        .getPayload().get("role", String.class);
    return role;
  }

  public Date getExpirationDate(String token) {
    Date role = Jwts
        .parser()
        .verifyWith(generateKey())
        .build().parseSignedClaims(token)
        .getPayload().getExpiration();
    return role;
  }

  public Boolean isValid(String token, String usernameInDB) {
    Boolean isValid = getExpirationDate(token).after(new Date()) && getSubject(token).equals(usernameInDB);
    return isValid;
  }

}
