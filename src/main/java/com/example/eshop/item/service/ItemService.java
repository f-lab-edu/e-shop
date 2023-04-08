package com.example.eshop.item.service;

import com.example.eshop.controller.dto.ItemDto;

public interface ItemService {
    void createItem(long adminSeq, ItemDto itemDto);
}
