package com.example.eshop.controller.v1.admin;

import com.example.eshop.common.dto.Result;
import com.example.eshop.common.type.TokenType;
import com.example.eshop.controller.dto.ItemCreationDto;
import com.example.eshop.controller.dto.ItemModificationDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemControllerTest {

    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private String accessToken;

    @Autowired
    public void setItemControllerTest(MockMvc mvc,
                                      ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public void login() throws Exception {
        String reqBody = objectMapper.writeValueAsString(
                new LoginDto("master", "asdf")
        );

        String loginResult = mvc.perform(post("/v1/admin/auth/login")
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
    @Transactional
    @DisplayName("createItem :: 정상 케이스")
    void createItem() throws Exception {
        String content = objectMapper.writeValueAsString(
                new ItemCreationDto(3L, "상품1",
                        "smallImageAddress",
                        "bigImageAddress",
                        10000,
                        "상품1 짧은 글 소개",
                        "상품사진 및 설명글",
                        20,
                        "Y")
        );

        mvc.perform(post("/v1/admin/items")
                        .header(TokenType.ACCESS.getHeaderName(), this.accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("getItems :: 정상 케이스")
    void getItems() throws Exception {
        mvc.perform(get("/v1/admin/items")
                        .header(TokenType.ACCESS.getHeaderName(), this.accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("getItem :: 정상 케이스")
    void getItem() throws Exception {
        mvc.perform(get("/v1/admin/items/1")
                        .header(TokenType.ACCESS.getHeaderName(), this.accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("modifyItem :: 정상 케이스")
    void modifyItem() throws Exception {
        String content = objectMapper.writeValueAsString(
                new ItemModificationDto(
                        "상품1 : updated",
                        10,
                        100000,
                        "상품1 짧은 글 소개 : updated",
                        "bigImageAddressFixed",
                        "smallImageAddressFixed",
                        "상품사진 및 설명글 : updated",
                        "Y",
                        "Y",
                        "Y")
        );

        mvc.perform(put("/v1/admin/items/1")
                        .header(TokenType.ACCESS.getHeaderName(), this.accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("deleteItem :: 정상 케이스")
    void deleteItem() throws Exception {
        mvc.perform(delete("/v1/admin/items/1")
                        .header(TokenType.ACCESS.getHeaderName(), this.accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}