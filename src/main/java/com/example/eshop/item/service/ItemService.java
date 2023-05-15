package com.example.eshop.item.service;

import com.example.eshop.common.dto.PageList;
import com.example.eshop.controller.dto.DetailedItemDto;
import com.example.eshop.controller.dto.ItemCreationDto;
import com.example.eshop.common.dto.PageRequestDto;
import com.example.eshop.controller.dto.ItemModificationDto;
import com.example.eshop.controller.dto.SimpleItemDto;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {
    SimpleItemDto createItem(long adminSeq, ItemCreationDto itemCreationDto, MultipartFile bigImage, MultipartFile smallImage);
    PageList<SimpleItemDto> getItems(Long adminSeq, PageRequestDto pageRequest);
    DetailedItemDto getItem(long itemSeq);
    void modifyItem(long itemSeq, ItemModificationDto request, MultipartFile bigImage, MultipartFile smallImage);
    void deleteItem(long itemSeq);
}
