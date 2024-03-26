package com.Auth.SpringSecurity.service;

import java.nio.charset.StandardCharsets;
import io.jsonwebtoken.Claims;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.*;
@Component
public class JwtUtils {
	
	private SecretKey key ;
	private  static  final long EXPIRATION_TIME = 86400000; //24h
	public JwtUtils(){
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }
	
	public String generateToken(UserDetails userDetails){
		HashMap<String, Object> claims = new HashMap<>();
		//claims.put("role", userDetails.getAuthorities());
        return Jwts.builder()
        		//.setClaims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
	
	 public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
		 claims.put("role", userDetails.getAuthorities());
	        return Jwts.builder()
	                .claims(claims)
	                .subject(userDetails.getUsername())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(key)
	                .compact();
	    }
	 
	 public String extractUsername(String token){
	        return extractClaims(token, Claims::getSubject);
	    }

	 private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
	        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
	    }
	 
	 public boolean isTokenValid(String token, UserDetails userDetails){
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	    public boolean isTokenExpired(String token){
	        return extractClaims(token, Claims::getExpiration).before(new Date());
	    }

}
