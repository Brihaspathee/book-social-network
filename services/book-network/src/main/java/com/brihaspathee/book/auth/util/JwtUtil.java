package com.brihaspathee.book.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/17/25
 * Time: 3:56 PM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.auth.util
 * To change this template use File | Settings | File and Code Template
 */
@Component
public class JwtUtil {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpirationInMs;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpirationInMs);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails,
                              long jwtExpirationInMs) {
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Function<Claims, Date> claimsResolver = Claims::getExpiration;
        return extractClaim(token, claimsResolver);
    }

    public String extractUsername(String token) {
        Function<Claims, String> claimsResolver = Claims::getSubject;
        return extractClaim(token, claimsResolver);
    }

    public List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<?> rawList = claims.get("authorities", List.class);
        return rawList.stream().map(Object::toString).toList();
    }

    public List<SimpleGrantedAuthority> extractGrantedAuthorities(String token) {
        List<String> authorities = extractAuthorities(token);
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

}
