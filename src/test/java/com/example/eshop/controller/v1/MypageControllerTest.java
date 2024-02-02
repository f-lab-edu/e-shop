package com.example.eshop.controller.v1;

import com.example.eshop.common.dto.Result;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.controller.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MypageControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private String accessToken;

    @Autowired
    public void setMypageControllerTest(MockMvc mvc,
                                      ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public void login() throws Exception {
        String reqBody = objectMapper.writeValueAsString(
                new LoginDto("hjkim@naver.com", "asdf")
        );

        String loginResult = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(reqBody))
                .andReturn()
                .getResponse().getContentAsString();

        Result result = objectMapper.readValue(loginResult, Result.class);
        LinkedHashMap<String, String> hashMap = (LinkedHashMap<String, String>) result.getData();

        this.accessToken = hashMap.get("accessToken");
    }

    @Test
    @DisplayName("getUser :: 정상 케이스")
    void getUser() throws Exception {
        mvc.perform(get("/v1/mypage/info")
                        .header(TokenType.ACCESS.getHeaderName(), this.accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());

    }

    @Test
    @DisplayName("getUser :: 토큰 누락 케이스")
    void getUserWithNoToken() throws Exception {
        mvc.perform(get("/v1/mypage/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }
}