package com.example.project1.security;

import com.example.project1.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private SecretKey key; // Key -> SecretKey로 변경 권장
    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @PostConstruct
    protected void init() {
        // 환경변수 값이 Base64 인코딩된 상태라면 아래 그대로 사용
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String generateToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .subject(user.getEmail()) // setClaims 대신 subject 바로 설정 가능
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(key) // 알고리즘 생략 가능 (key 크기에 맞춰 자동 선택)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // parserBuilder() -> parser()로 변경됨
            Jwts.parser()
                    .verifyWith(key) // setSigningKey -> verifyWith
                    .build()
                    .parseSignedClaims(token); // parseClaimsJws -> parseSignedClaims
            return true;
        } catch (Exception e) {
            return false; // 검증 실패 시 false 반환
        }
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload() // getBody -> getPayload
                .getSubject();
    }

    public Long getExpirationTime(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .getTime();
    }
}
