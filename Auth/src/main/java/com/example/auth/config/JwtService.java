package com.example.auth.config;

import com.example.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "665ab6314a1675593615c1619a67053925bc8cd5140d77256f3fd0f4a84877f5";

    // Ekstrakcija korisničkog imena (subject) iz tokena
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Ekstrakcija bilo kojeg claim-a iz tokena
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generisanje JWT tokena sa korisničkim podacima
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generisanje JWT tokena sa dodatnim claim-ovima
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        User user = (User) userDetails;  // Pretpostavljamo da je userDetails tipa User

        // Dodavanje korisničkih podataka kao claims u token
        extraClaims.put("firstName", user.getFirstName());
        extraClaims.put("lastName", user.getLastName());
        extraClaims.put("email", user.getEmail());
        extraClaims.put("uuid", user.getUuid());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail()) // Koristimo email kao subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // token važi 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Provera da li je token validan
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Provera da li je token istekao
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Ekstrakcija datuma isteka iz tokena
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Ekstrakcija svih claim-ova iz tokena
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Dohvatanje signing ključa iz SECRET_KEY
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Ekstrakcija firstName iz tokena
    public String extractFirstName(String token) {
        return extractClaim(token, claims -> claims.get("firstName", String.class));
    }

    // Ekstrakcija lastName iz tokena
    public String extractLastName(String token) {
        return extractClaim(token, claims -> claims.get("lastName", String.class));
    }

    // Ekstrakcija email iz tokena
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    // Ekstrakcija UUID iz tokena
    public String extractUuid(String token) {
        return extractClaim(token, claims -> claims.get("uuid", String.class));
    }
}
