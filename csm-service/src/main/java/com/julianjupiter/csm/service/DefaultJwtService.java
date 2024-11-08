package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.TokenDto;
import com.julianjupiter.csm.security.JwtUser;
import com.julianjupiter.csm.util.Uuid;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Julian Jupiter
 */
@Service
class DefaultJwtService implements JwtService {
    private final String applicationName;
    private final String secretKey;
    private final long expiration;

    DefaultJwtService(
            @Value("${spring.application.name}") String applicationName,
            @Value("${application.security.jwt.secret-key}") String secretKey,
            @Value("${application.security.jwt.expiration}") long expiration
    ) {
        this.applicationName = applicationName;
        this.secretKey = secretKey;
        this.expiration = expiration;
    }

    @Override
    public TokenDto generateToken(Authentication authentication) {
        var user = (JwtUser) authentication.getPrincipal();
        var claims = Map.of(
                "user_id", user.getId(),
                "name", user.getName(),
                "first_name", user.getFirstName(),
                "last_name", user.getLastName(),
                "email", user.getEmail(),
                "roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
        );

        return this.createToken(claims, user.getUsername());
    }

    @Override
    public void validateToken(String accessToken) {
        Jwts.parser()
                .verifyWith((SecretKey) this.getSignKey())
                .build()
                .parse(accessToken);
    }

    @Override
    public String extractUsername(String accessToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) this.getSignKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject();
    }

    private TokenDto createToken(Map<String, Object> claims, String username) {
        long currentTimeMillis = System.currentTimeMillis();
        var expiresIn = new Date(currentTimeMillis + this.expiration);
        var accessToken = Jwts.builder()
                .id(Uuid.create().toString())
                .issuer(this.applicationName)
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(expiresIn)
                .signWith(getSignKey())
                .compact();

        return new TokenDto(accessToken, expiresIn, "Bearer");
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
