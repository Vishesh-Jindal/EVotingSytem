package com.example.eVoting.jwt;

import com.example.eVoting.exceptions.TokenException;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationHelper {
    private String secret = "fefevjnkijojoi3rfefnjenlkfldnoirjf0rof3rifropnmlknfdwfo9p0o9fn3relkmv0p3j";
    private static final long JWT_TOKEN_VALIDITY = 60*60;

    public String getUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    public Claims getClaims(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(secret.getBytes())
                    .build().parseClaimsJws(token).getBody();
        } catch (Exception e){
            throw new TokenException("Problem in Parsing the token");
        }
    }
    public Boolean isTokenExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName()),SignatureAlgorithm.HS512)
                .compact();
    }
}
