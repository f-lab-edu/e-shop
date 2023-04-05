package com.example.eshop.item.service.impl;

import com.example.eshop.controller.dto.ItemDto;
import com.example.eshop.item.model.ItemEntity;
import com.example.eshop.item.repository.ItemRepository;
import com.example.eshop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void createItem(long itemSeq, ItemDto itemDto) {
        log.info("createItem ::: {}", itemDto);

        ItemEntity item = ItemEntity.builder()
                .adminNo(itemSeq)
                .categoryNo(itemDto.getCategorySeq())
                .name(itemDto.getName())
                .smallImage(itemDto.getSmallImage())
                .bigImage(itemDto.getBigImage())
                .price(itemDto.getPrice())
                .intro(itemDto.getIntro())
                .content(itemDto.getContent())
                .remains(itemDto.getRemains())
                .fastYn(itemDto.getFastYn())
                .build();

        itemRepository.insertItem(item);
    }
}
