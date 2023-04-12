package com.example.eshop.item.service.impl;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.admin.member.core.service.AdminMemberService;
import com.example.eshop.common.dto.PageList;
import com.example.eshop.controller.dto.DetailedItemDto;
import com.example.eshop.controller.dto.ItemDto;
import com.example.eshop.common.dto.PageRequestDto;
import com.example.eshop.controller.dto.SimpleItemDto;
import com.example.eshop.item.model.ItemEntity;
import com.example.eshop.item.repository.ItemRepository;
import com.example.eshop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final AdminMemberService adminMemberService;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void createItem(long adminSeq, ItemDto itemDto) {
        log.info("createItem ::: {} {}", adminSeq, itemDto);

        ItemEntity item = ItemEntity.builder()
                .adminNo(adminSeq)
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

    @Override
    public PageList<SimpleItemDto> getItems(Long adminSeq, PageRequestDto pageRequest) {
        log.info("createItem ::: {} {}", adminSeq, pageRequest);
        int totalCount = itemRepository.getTotalCount(adminSeq);
        List<ItemEntity> items = itemRepository.selectItems(adminSeq);
        
        // TODO : 상품 등록자 아이디
        List<SimpleItemDto> simpleItems = new ArrayList<>();
        return new PageList<>(pageRequest.getPageSize(), totalCount, simpleItems);
    }

    @Override
    public DetailedItemDto getItem(long itemSeq) {
        log.info("getItem ::: {}", itemSeq);

        ItemEntity item = itemRepository.selectItem(itemSeq);
        AdminUserEntity seller = adminMemberService.getAdminUserByUserNo(item.getAdminNo());

        return new DetailedItemDto(item, seller.getAdminId());
    }
}
