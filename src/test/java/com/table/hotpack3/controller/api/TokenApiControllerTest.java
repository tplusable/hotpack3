package com.table.hotpack3.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.table.hotpack3.config.jwt.JwtFactory;
import com.table.hotpack3.config.jwt.JwtProperties;
import com.table.hotpack3.domain.RefreshToken;
import com.table.hotpack3.domain.User;
import com.table.hotpack3.dto.request.CreateAccessTokenRequest;
import com.table.hotpack3.repository.RefreshTokenRepository;
import com.table.hotpack3.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();;
    }

    @DisplayName("createNewAccessToken: 새로운 AccessToken발급")
    @Test
    public void createNewAccessToken() throws Exception{
        final String url="/api/token";

        User testUser=userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        String refreshToken= JwtFactory.builder()
                .claims(Map.of("id", testUser.getUserId()))
                .build()
                .createToken(jwtProperties);

        refreshTokenRepository.save(new RefreshToken(testUser.getUserId(), refreshToken));

        CreateAccessTokenRequest request=new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToken);
        final String requestBody=objectMapper.writeValueAsString(request);

        ResultActions resultActions =mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

}