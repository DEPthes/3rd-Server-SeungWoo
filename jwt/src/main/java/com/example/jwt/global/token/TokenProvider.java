package com.example.jwt.global.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dormease.dormeasedev.domain.auth.domain.RefreshToken;
import dormease.dormeasedev.domain.auth.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class TokenProvider {

    private final String secretKey;
    private final long expirationMinutes;
    private final long refreshExpirationHours;
    private final String issuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-minutes}") long expirationMinutes,
            @Value("${refresh-expiration-hours}") long refreshExpirationHours,
            @Value("${issuer}") String issuer,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.secretKey = secretKey;
        this.expirationMinutes = expirationMinutes;
        this.refreshExpirationHours = refreshExpirationHours;
        this.issuer = issuer;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(userSpecification)  // JWT 토큰 제목
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    // 기존 액세스 토큰을 토대로 새로운 액세스 토큰을 생성
    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        refreshTokenRepository.findByAccount(subject.split(":")[0])
                .ifPresentOrElse(
                        RefreshToken::increaseReissueCount,
                        () -> { throw new ExpiredJwtException(null, null, "Refresh token expired."); }
                );
        return createAccessToken(subject);
    }

    public String validateTokenAndGetSubject(String token) {
        return validateAndParseToken(token)
                .getBody()
                .getSubject();
    }

    // 리프레시 토큰이 유효한 토큰인지를 검증
    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String account = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
        refreshTokenRepository.findByAccount(account)
                .filter(userRefreshToken -> userRefreshToken.validateRefreshToken(refreshToken))
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh token expired."));
    }

    // 내부의 parseCliamJws()에서 JWT를 파싱할 때, 토큰이 유효한지 검사하여 예외를 던지기 때문에 토큰 검증에 사용
    private Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
//                .setIssuer(issuer)
//                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))
                .compact();
    }

}
