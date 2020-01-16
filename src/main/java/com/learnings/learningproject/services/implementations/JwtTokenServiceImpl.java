package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.services.IJwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenServiceImpl implements IJwtTokenService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String getUserNameFromToken(String token) {
        String username = getClaimFromToken(token, Claims::getSubject);

        if (username == null || username.isEmpty())
        {
            username = getClaimFromToken(token, Claims::getIssuer);
        }

        return username;
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimFromToken(token, Claims::getExpiration);

        if (expiration == null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getClaimFromToken(token, Claims::getIssuedAt));
            calendar.add(Calendar.SECOND, (int)JWT_TOKEN_VALIDITY);
            expiration = calendar.getTime();
        }

        return expiration;
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        if (claims == null) return null;
        return claimsResolver.apply(claims);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claim;
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) return true;
        return expiration.before(new Date());
    }
}
