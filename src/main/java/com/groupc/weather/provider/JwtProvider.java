package com.groupc.weather.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.groupc.weather.common.model.AuthenticationObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String create(String email,Object isManager) {

        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        Map<String, Object> managerMap = new HashMap<>();
        managerMap.put("key", isManager);

        Claims claims = Jwts.claims(managerMap).setSubject(email).setIssuedAt(expireDate)
        .setExpiration(expireDate);

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claims)
                .compact();

        return jwt;
    }
    public AuthenticationObject validate(String jwt) {
        AuthenticationObject authenticationObject = null;

        try {
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    
            String email = claims.getSubject();
            boolean isManager = (Boolean) claims.get("key");

            authenticationObject = new AuthenticationObject(email, isManager);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return authenticationObject;
    }
}