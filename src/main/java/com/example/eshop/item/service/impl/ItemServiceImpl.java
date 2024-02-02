package com.example.eshop.item.service.impl;

import com.example.eshop.admin.category.model.CategoryEntity;
import com.example.eshop.admin.category.service.CategoryService;
import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.admin.member.core.service.AdminMemberService;
import com.example.eshop.common.dto.PageList;
import com.example.eshop.common.exception.DataNotFoundException;
import com.example.eshop.common.exception.FileCreationFailedException;
import com.example.eshop.common.exception.SaveImageFailedException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Value("${item.big-image-file.path}")
    String bigImageFilePath;

    @Value("${item.small-image-file.path}")
    String smallImageFilePath;

    @Override
    @Transactional
    public SimpleItemDto createItem(long adminSeq, ItemCreationDto itemCreationDto,
                                    MultipartFile bigImage, MultipartFile smallImage) {
        log.info("createItem ::: {} {}", adminSeq, itemCreationDto);

        ItemEntity item = ItemEntity.builder()
                .adminNo(adminSeq)
                .categoryNo(itemCreationDto.getCategorySeq())
                .name(itemCreationDto.getName())
                .price(itemCreationDto.getPrice())
                .intro(itemCreationDto.getIntro())
                .content(itemCreationDto.getContent())
                .remains(itemCreationDto.getRemains())
                .fastYn(itemCreationDto.getFastYn())
                .build();

        AdminUserEntity seller = adminMemberService.getAdminUserByUserNo(item.getAdminNo());

        itemRepository.insertItem(item);
        saveImage(bigImageFilePath + "/" + item.getItemNo(), bigImage);
        saveImage(smallImageFilePath + "/" + item.getItemNo(), smallImage);

        return new SimpleItemDto(item, seller);
    }

    @Override
    public PageList<SimpleItemDto> getItems(Long adminSeq, PageRequestDto pageRequest) {
        log.info("createItem ::: {} {}", adminSeq, pageRequest);
        int totalCount = itemRepository.getTotalCount(adminSeq);
        List<ItemEntity> items = itemRepository.selectItems(adminSeq, pageRequest);
        List<Long> sellerSeqList = items.stream().map(ItemEntity::getAdminNo).distinct().collect(Collectors.toList());

        List<SimpleItemDto> simpleItems = new ArrayList<>();
        if (!sellerSeqList.isEmpty()) {
            List<AdminUserEntity> sellerList = adminMemberService.getAdminUserListByUserNo(sellerSeqList);
            Map<Long, AdminUserEntity> sellerMap = sellerList.stream()
                    .collect(Collectors.toMap(AdminUserEntity::getAdminNo, Function.identity()));

            for (ItemEntity item : items) {
                SimpleItemDto simpleItem = new SimpleItemDto(item, sellerMap.get(item.getAdminNo()));
                simpleItems.add(simpleItem);
            }
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
    public void modifyItem(long itemSeq, ItemModificationDto request, MultipartFile bigImage, MultipartFile smallImage) {
        log.info("modifyItem ::: {} {}", itemSeq, request);

        ItemEntity item = itemRepository.selectItem(itemSeq);
        checkItemExist(item);

        updateItemEntityParam(request, item);
        saveImage(bigImageFilePath + "/" + item.getItemNo(), bigImage);
        saveImage(smallImageFilePath + "/" + item.getItemNo(), smallImage);

        itemRepository.updateItem(item);
    }

    @Override
    @Transactional
    public void deleteItem(long itemSeq) {
        log.info("deleteItem ::: {}", itemSeq);

        itemRepository.deleteItem(itemSeq);
    }

    private void saveImage(String filePath, MultipartFile image) {
        checkFileExist(image);

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new FileCreationFailedException();
        }

        try {
            image.transferTo(file);
        } catch (IOException e) {
            throw new SaveImageFailedException();
        }
    }

    private void updateItemEntityParam(ItemModificationDto request, ItemEntity item) {
        item.setName(request.getName());
        item.setRemains(request.getRemains());
        item.setPrice(request.getPrice());
        item.setIntro(request.getIntro());
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

    private void checkFileExist(MultipartFile image) {
        if (image.isEmpty()) {
            throw new DataNotFoundException();
        }
    }
}
