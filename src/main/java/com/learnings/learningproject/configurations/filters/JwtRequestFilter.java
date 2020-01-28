package com.learnings.learningproject.configurations.filters;

import com.learnings.learningproject.services.IAuthService;
import com.learnings.learningproject.services.IJwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private IAuthService authService;
    private IJwtTokenService jwtTokenService;

    @Autowired
    public JwtRequestFilter(IAuthService authService, IJwtTokenService jwtTokenService) {
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Remove Bearer from token - formatting token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
        {
            jwtToken = requestTokenHeader.replace("Bearer ", "");
            try
            {
                username = jwtTokenService.getUserNameFromToken(jwtToken);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Unable to get JWT Token");
                logger.info("Unable to get JWT Token");
            }
            catch (ExpiredJwtException e)
            {
                System.out.println("JWT Token has expired");
                logger.info("JWT Token has expired");
            }
        }
        else
        {
            if (requestTokenHeader != null && !requestTokenHeader.isEmpty())
            {
                logger.info("Invalid token provided.");
            }
        }

        // Verify the token validity.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = this.authService.getUserById(username);
            // if token is valid configure Spring Security to manually set authentication
            if (userDetails != null && this.jwtTokenService.validateToken(jwtToken, userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else
            {
                logger.info("Invalid user : " + username);
            }
        }
        chain.doFilter(request, response);
    }

}
