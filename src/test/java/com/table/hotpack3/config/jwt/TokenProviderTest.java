package com.table.hotpack3.config.jwt;

import com.table.hotpack3.domain.User;
import com.table.hotpack3.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.apache.groovy.util.Maps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저정보와 만료기간을 전달해 토큰생성")
    @Test
    void generateToken() {
        User testUser = userRepository.save(User.builder()
                .email("user2@gmail.com")
                .password("test")
                .build());

        String token= tokenProvider.generateToken(testUser, Duration.ofDays(14));

        Long userId= Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getUserId());
    }

    @DisplayName("validToken(): 만료된 토큰일때 유효성 검증 실패")
    @Test
    void validToken_invalidToken() {
        String token=JwtFactory.builder()
                .expiration(new Date(new Date().getTime()-Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        boolean result=tokenProvider.validToken(token);
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰일대 유효성 검증 성공")
    @Test
    void validToken_validToken() {
        String token=JwtFactory.withDefaultValues().createToken(jwtProperties);

        boolean result=tokenProvider.validToken(token);

        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰기반 인증정보조회")
    @Test
    void getAuthentication() {
        String userEmail="user@email.com";
        String token=JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        Authentication authentication=tokenProvider.getAuthentication(token);

        assertThat(((UserDetails)authentication.getPrincipal()).getUsername())
                .isEqualTo(userEmail);
    }

    @DisplayName("getUserId(): 토큰으로 유저ID 조회")
    @Test
    void getUserId() {
        Long userId=1L;
        String token = JwtFactory.builder()
                .claims(Maps.of("id", userId))
                .build()
                .createToken(jwtProperties);

        Long userIdByToken= tokenProvider.getUserId(token);

        assertThat(userIdByToken).isEqualTo(userId);
    }
}
