package com.unq.dapp_grupo_e.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Map;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    //@Value("${jwt.secret}")
    private static final String SECRET="BANRASRLIAPOPPARHELHAPWORAFOWPASTTESMORICAEAM";

    public String createToken(Map<String, Object> claims, String subject) {
        Date createdAt = new Date();
        Date expirationToken = new Date(System.currentTimeMillis() + (1000*60*180));
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdAt)
            .setExpiration(expirationToken)
            .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateToken(String email) {
        Map<String, Object> claims = Map.of();
        return createToken(claims, email);
    }

    public String getEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String email = getEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    
    public Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
    
}


