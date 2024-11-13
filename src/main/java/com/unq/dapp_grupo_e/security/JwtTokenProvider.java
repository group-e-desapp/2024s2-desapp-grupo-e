package com.unq.dapp_grupo_e.security;


import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {
    
    //@Value("${jwt.secret}")
    private static final String SECRET="BANRASRLIAPOPPARHELHAPWORAFOWPASTTESMORICAEAM";

    @SuppressWarnings("deprecation")
    public String generateToken(UserDetails user) {
        //String email = user.getUsername();
        Date createdAt = new Date();
        Date expirationToken = new Date(System.currentTimeMillis() + (1000*60*90));

        return Jwts
            .builder()
            .setSubject(user.getUsername())
            .setIssuedAt(createdAt)
            .setExpiration(expirationToken)
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getKey() {
        byte[] keyBite = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBite);
    }
}
