package com.example.eshop.controller.v1.admin;

import com.example.eshop.controller.dto.DetailedItemDto;
import com.example.eshop.controller.dto.ItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Autowired
    public void setItemControllerTest(MockMvc mvc,
                                      ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    // TODO : accessToken 생성

    @Test
    @Transactional
    @DisplayName("createItem :: 정상 케이스")
    void createItem() throws Exception {
        String content = objectMapper.writeValueAsString(
                new ItemDto(3L, "상품1",
                        "smallImageAddress",
                        "bigImageAddress",
                        10000,
                        "상품1 짧은 글 소개",
                        "상품사진 및 설명글",
                        20,
                        "Y")
        );

        mvc.perform(post("/v1/admin/items")
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("getItem :: 정상 케이스")
    void getItem() throws Exception {
        mvc.perform(get("/v1/admin/items/1")
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
                new DetailedItemDto(3L,
                        "상품1 : updated",
                        "Y",
                        "smallImageAddressFixed",
                        "bigImageAddressFixed",
                        100000,
                        "상품1 짧은 글 소개 : updated",
                        "상품사진 및 설명글 : updated",
                        10,
                        "0",
                        "Y",
                        "Y")
        );

        mvc.perform(put("/v1/admin/items/1")
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}