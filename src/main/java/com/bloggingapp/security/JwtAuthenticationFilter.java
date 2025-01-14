package com.bloggingapp.security;

import com.bloggingapp.exception.CustomServiceException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private CustomUserDetailService customUserDetailService;
    private JwtTokenHelper jwtTokenHelper;

    public JwtAuthenticationFilter (CustomUserDetailService customUserDetailService, JwtTokenHelper jwtTokenHelper) {
        this.customUserDetailService = customUserDetailService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String servletPath = request.getServletPath();
            if (servletPath.equals("/api/auth/login")) {
                filterChain.doFilter(request, response);
                return;
            }
            //1. Getting JWT from request
            String requestToken = request.getHeader("Authorization");

            if (requestToken == null || !requestToken.startsWith("Bearer")) {
                throw new IllegalArgumentException("Request token is invalid");
            }
            String token = requestToken.substring(7);
            String userName = this.jwtTokenHelper.getUserNameFromToken(token);

            //2. Validate token
            if (userName == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                throw new IllegalArgumentException("Username empty of security context already set");
            }

            //3. Validation + Getting user from token
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userName);

            if (!this.jwtTokenHelper.isTokenValid(token, userDetails.getUsername())) {
                throw new CustomServiceException("Token validation failed");
            }

            //4. Setting spring security
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(request, response);

        } catch (IllegalArgumentException e) {
            System.out.println(e);
            throw new CustomServiceException("Invalid token data");
        } catch (ExpiredJwtException e) {
            System.out.println(e);
            throw new CustomServiceException("Expired token received");
        } catch (MalformedJwtException e) {
            System.out.println(e);
            throw new CustomServiceException("Malformed jwt token");
        } catch (CustomServiceException e) {
            System.out.println(e);
            throw new CustomServiceException(e.getErrorMessage());
        }
    }
}
