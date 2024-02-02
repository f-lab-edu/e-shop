package com.example.eshop.controller.v1.admin;

import com.example.eshop.common.dto.Result;
import com.example.eshop.common.type.MemberType;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.controller.dto.AdminUserDto;
import com.example.eshop.controller.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    String loginDto;

    @Autowired
    public void setAuthControllerTest(MockMvc mvc,
                                      ObjectMapper objectMapper) throws JsonProcessingException {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
        this.loginDto = objectMapper.writeValueAsString(
                new LoginDto("master@naver.com", "asdf")
        );
    }

    @Test
    @DisplayName("checkDuplicatedId :: 파라미터 미입력 케이스")
    void checkDuplicatedIdWithNull() throws Exception {
        mvc.perform(get("/v1/admin/auth/check/duplicated-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("checkDuplicatedId :: 중복아이디 존재 케이스")
    void checkDuplicatedIdWithExistId() throws Exception {
        mvc.perform(get("/v1/admin/auth/check/duplicated-id?id=master@naver.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("checkDuplicatedId :: 중복아이디 미존재 케이스")
    void checkDuplicatedIdWithNotExistId() throws Exception {
        mvc.perform(get("/v1/admin/auth/check/duplicated-id?id=asdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(false))
                .andDo(print());
    }



    @Test
    @Transactional
    @DisplayName("signin :: 정상 케이스")
    void signin() throws Exception {
        String content = objectMapper.writeValueAsString(
                new AdminUserDto("test", "test", MemberType.ADMIN, "test",
                        "01012341234", "000001",
                        "Seoul")
        );

        mvc.perform(post("/v1/admin/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    @Transactional
    @DisplayName("login :: 성공 케이스")
    MvcResult login() throws Exception {
        return mvc.perform(post("/v1/admin/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(this.loginDto))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    @DisplayName("login :: 실패 케이스")
    MvcResult loginFailed() throws Exception {
        String failedLoginRequest = objectMapper.writeValueAsString(
                new LoginDto("test", "1234")
        );
        return mvc.perform(post("/v1/admin/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(failedLoginRequest))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }



    @Test
    @Transactional
    @DisplayName("refreshToken :: 정상 케이스")
    void refreshToken() throws Exception {

        MvcResult loginResult = login();

        String stringResult = loginResult.getResponse().getContentAsString();
        Result result = objectMapper.readValue(stringResult, Result.class);
        LinkedHashMap<String, String> hashMap = (LinkedHashMap<String, String>) result.getData();

        mvc.perform(get("/v1/admin/auth/token/refresh")
                        .header(TokenType.REFRESH.getHeaderName(), hashMap.get("refreshToken"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("refreshToken :: 토큰 누락 케이스")
    void refreshTokenWithNoToken() throws Exception {
        mvc.perform(get("/v1/admin/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
