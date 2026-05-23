package com.seng.management_system.service.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.seng.management_system.data_model.UserLoginDataModel;
import com.seng.management_system.dto.jwt.JwtResponse;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.model.UserLogin;
import com.seng.management_system.repository.UserInfoRepository;
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
    UserInfoRepository userInfoRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Long getUserIdFromToken(String token) {

        if (token == null) {
            return null;
        }

        token = token.replace("Bearer ", "");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object id = claims.get("id");

        return id != null ? Long.valueOf(id.toString()) : null;
    }

    public JwtResponse generateToken(UserLoginDataModel username) {

        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + expiration);

        UserLogin userLogin = userLoginRepository.findByUsername(username.getUsername()).orElse(new UserLogin());

        UserInfo userInfo = userInfoRepository.findByUserLoginId(userLogin.getId());

        String token = Jwts.builder()
                .setSubject(username.getUsername())
                .claim("id", userInfo.getId())
                .claim("email", userInfo.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        // Long id = userLoginRepository.findByUsername(username).map(UserLogin::getId).orElse(0L);
        JwtResponse res = new JwtResponse();
        res.setExpiredAt(expiryDate);
        res.setUserInfo(userInfo);
        res.setToken(token);
        return res;
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
