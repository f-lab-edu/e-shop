package com.example.eshop.item.service.impl;

import com.example.eshop.admin.category.model.CategoryEntity;
import com.example.eshop.admin.category.service.CategoryService;
import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.admin.member.core.service.AdminMemberService;
import com.example.eshop.common.dto.PageList;
import com.example.eshop.common.exception.DataNotFoundException;
import com.example.eshop.controller.dto.DetailedItemDto;
import com.example.eshop.controller.dto.ItemCreationDto;
import com.example.eshop.common.dto.PageRequestDto;
import com.example.eshop.controller.dto.ItemModificationDto;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final AdminMemberService adminMemberService;
    private final CategoryService categoryService;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public SimpleItemDto createItem(long adminSeq, ItemCreationDto itemCreationDto) {
        log.info("createItem ::: {} {}", adminSeq, itemCreationDto);

        ItemEntity item = ItemEntity.builder()
                .adminNo(adminSeq)
                .categoryNo(itemCreationDto.getCategorySeq())
                .name(itemCreationDto.getName())
                .smallImage(itemCreationDto.getSmallImage())
                .bigImage(itemCreationDto.getBigImage())
                .price(itemCreationDto.getPrice())
                .intro(itemCreationDto.getIntro())
                .content(itemCreationDto.getContent())
                .remains(itemCreationDto.getRemains())
                .fastYn(itemCreationDto.getFastYn())
                .build();

        AdminUserEntity seller = adminMemberService.getAdminUserByUserNo(item.getAdminNo());

        itemRepository.insertItem(item);

        return new SimpleItemDto(item, seller);
    }

    @Override
    public PageList<SimpleItemDto> getItems(Long adminSeq, PageRequestDto pageRequest) {
        log.info("createItem ::: {} {}", adminSeq, pageRequest);
        int totalCount = itemRepository.getTotalCount(adminSeq);
        List<ItemEntity> items = itemRepository.selectItems(adminSeq);
        List<Long> sellerSeqList = items.stream().map(ItemEntity::getAdminNo).distinct().collect(Collectors.toList());

        List<AdminUserEntity> sellerList = adminMemberService.getAdminUserListByUserNo(sellerSeqList);
        Map<Long, AdminUserEntity> sellerMap = sellerList.stream()
                .collect(Collectors.toMap(AdminUserEntity::getAdminNo, Function.identity()));

        List<SimpleItemDto> simpleItems = new ArrayList<>();
        for (ItemEntity item : items) {
            SimpleItemDto simpleItem = new SimpleItemDto(item, sellerMap.get(item.getAdminNo()));
            simpleItems.add(simpleItem);
        }
        return new PageList<>(pageRequest.getPageSize(), totalCount, simpleItems);
    }

    @Override
    public DetailedItemDto getItem(long itemSeq) {
        log.info("getItem ::: {}", itemSeq);

        ItemEntity item = itemRepository.selectItem(itemSeq);
        checkItemExist(item);

        AdminUserEntity seller = adminMemberService.getAdminUserByUserNo(item.getAdminNo());

        CategoryEntity category = categoryService.getCategoryByCategoryNo(item.getCategoryNo());

        return new DetailedItemDto(item, seller, category);
    }

    @Override
    @Transactional
    public void modifyItem(long itemSeq, ItemModificationDto request) {
        log.info("modifyItem ::: {} {}", itemSeq, request);

        ItemEntity item = itemRepository.selectItem(itemSeq);
        checkItemExist(item);

        updateItemEntityParam(request, item);

        itemRepository.updateItem(item);
    }

    @Override
    @Transactional
    public void deleteItem(long itemSeq) {
        log.info("deleteItem ::: {}", itemSeq);

        itemRepository.deleteItem(itemSeq);
    }

    private void updateItemEntityParam(ItemModificationDto request, ItemEntity item) {
        item.setName(request.getName());
        item.setRemains(request.getRemains());
        item.setPrice(request.getPrice());
        item.setIntro(request.getIntro());
        item.setBigImage(request.getBigImage());
        item.setSmallImage(request.getSmallImage());
        item.setContent(request.getContent());
        item.setAdYn(request.getAdYn());
        item.setMdRecommendYn(request.getMdRecommendYn());
        item.setFastYn(request.getFastYn());
    }

    private void checkItemExist(ItemEntity item) {
        if (item == null) {
            throw new DataNotFoundException();
        }

        if (item.getAdminNo() == 0) {
            throw new DataNotFoundException();
        }
    }
}
