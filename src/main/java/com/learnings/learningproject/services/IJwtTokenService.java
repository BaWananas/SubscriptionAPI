package com.learnings.learningproject.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface IJwtTokenService {
    String getUserNameFromToken(String token);
    Date getExpirationDateFromToken(String token);
    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
    boolean validateToken(String token, UserDetails userDetails);
}
