package com.spring.bank.Project.bank;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
@Service
 class JwtService{
//GENERATES SECRET KEY
    private final SecretKey secretKey=Keys.secretKeyFor(SignatureAlgorithm.HS256);

//GENERATES JWT TOKEN
    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(secretKey)
                .compact();

    }
 //VERIFY TOKEN AND EXTRACT EMAIL
    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
//VALIDATES JWT TOKEN
    public boolean isTokenValid (String token){
        try{
            extractEmail(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
