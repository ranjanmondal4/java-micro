package com.micro.zuul.configuration;

import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.repo.RedisUserRoleRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private String secretKey;

    @Autowired
    RedisUserRoleRepo userRoleRepo;

    @Autowired
    EnvironmentVariables environmentVariables;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(environmentVariables.getJwtSecretKey().getBytes());
    }

    public String createToken(String email, String userId, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", userId);
        claims.put("roles", roles.stream().map(s -> new SimpleGrantedAuthority(s)).filter(Objects::nonNull)
                .collect(Collectors.toList()));

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + environmentVariables.getJwtExpirationInMills());

        return Jwts.builder()//
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .setIssuedAt(now)
                .setIssuer("zuul-service")
                .setAudience("user-service")
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public RedisUserRole getUser(String token) {
        String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id", String.class);
        List<String> roles = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("roles", List.class);
        return RedisUserRole.of(userId, token, roles);
    }

    public String removeToken(String token) {
        if (token != null && token.startsWith(AppConstants.BEARER_WITH_SPACE)) {
            return token.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
//            log.info("::::::::: exception {}", e.getMessage());
            throw new RuntimeException("Expired or invalid JWT token");
        }
    }

}
