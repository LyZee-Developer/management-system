package com.seng.management_system.service.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.seng.management_system.dto.jwt.JwtResponse;
import com.seng.management_system.model.UserLogin;
import com.seng.management_system.repository.UserLoginRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    UserLoginRepository userLoginRepository;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtResponse generateToken(String username) {

        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + expiration);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        Long id = userLoginRepository.findByUsername(username).map(UserLogin::getId).orElse(0L);
        return new JwtResponse(
                id,
                token,
                username,
                expiryDate,
                "Bearer"
        );
    }

    // public String generateToken(String username) {
    //     return Jwts.builder()
    //             .setSubject(username)
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + expiration))
    //             .signWith(getSignKey(), SignatureAlgorithm.HS256)
    //             .compact();
    // }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
