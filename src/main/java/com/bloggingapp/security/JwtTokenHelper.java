package com.bloggingapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    public Key key = Jwts.SIG.HS512.key().build();

    public String getUserNameFromToken (String jwtToken) {
        return getClaimsFromToken (jwtToken, Claims::getSubject);
    }

    public Date getExpirationDateFromToken (String jwtToken) {
        return getClaimsFromToken (jwtToken, Claims::getExpiration);
    }

    public <T> T getClaimsFromToken (String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken (jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken (String jwtToken) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(jwtToken).getPayload();
    }

    private Boolean isTokenExpired (String jwtToken) {
        Date expirationDate = this.getExpirationDateFromToken(jwtToken);
        return expirationDate.before(new Date());
    }

    public String generateNewToken (UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }

    public String generateToken (Map claims, String userName) {
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + JWT_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }

    public Boolean isTokenValid (String jwtToken, String userName) {
        String jwtUserName = getUserNameFromToken(jwtToken);
        return ((userName.equals(jwtUserName)) && !isTokenExpired(jwtToken));
    }
}
