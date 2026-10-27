package com.velora.api.security;

import com.velora.api.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final SecretKey key;
  private final long expiry;

  public JwtService(
      @Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long expiry) {
    if (secret.getBytes(StandardCharsets.UTF_8).length < 32)
      throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expiry = expiry;
  }

  public String generate(User u) {
    return Jwts.builder()
        .subject(u.getEmail())
        .claim("role", u.getRole().name())
        .claim("uid", u.getId())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiry))
        .signWith(key)
        .compact();
  }

  public String subject(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
  }
}
