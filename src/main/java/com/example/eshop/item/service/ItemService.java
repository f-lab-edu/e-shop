package com.example.eshop.item.service;

import com.example.eshop.common.dto.PageList;
import com.example.eshop.controller.dto.DetailedItemDto;
import com.example.eshop.controller.dto.ItemDto;
import com.example.eshop.common.dto.PageRequestDto;
import com.example.eshop.controller.dto.SimpleItemDto;

public interface ItemService {
    void createItem(long adminSeq, ItemDto itemDto);
    PageList<SimpleItemDto> getItems(Long adminSeq, PageRequestDto pageRequest);
    DetailedItemDto getItem(long itemSeq);
    void modifyItem(long itemSeq, DetailedItemDto request);
    void deleteItem(long itemSeq);
}
