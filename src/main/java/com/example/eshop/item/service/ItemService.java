package com.example.eshop.item.service;

import com.example.eshop.controller.dto.ItemDto;
import com.example.eshop.controller.dto.PageRequestDto;

import java.util.List;

public interface ItemService {
    void createItem(long adminSeq, ItemDto itemDto);
    List<ItemDto> getItems(Long adminSeq, PageRequestDto pageRequest);
}
