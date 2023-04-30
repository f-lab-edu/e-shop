package com.example.eshop.controller.v1.admin;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.aop.admin.Admin;
import com.example.eshop.aop.admin.AdminLoginCheck;
import com.example.eshop.controller.dto.DetailedItemDto;
import com.example.eshop.controller.dto.ItemDto;
import com.example.eshop.common.dto.PageList;
import com.example.eshop.common.dto.PageRequestDto;
import com.example.eshop.controller.dto.SimpleItemDto;
import com.example.eshop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("AdminItemController")
@RequestMapping(value="/v1/admin/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 등록
     *
     * @author hjkim
     * @param admin, item
     */
    @AdminLoginCheck
    @PostMapping(value="")
    public SimpleItemDto createItem(@Admin AdminUserEntity admin,
                           @RequestBody ItemDto item) {
        log.info("createItem ::: {} {}", admin, item);

        return itemService.createItem(admin.getAdminNo(), item);
    }

    /**
     * 상품 리스트 조회
     *
     * @author hjkim
     * @param admin, pageRequest
     */
    @AdminLoginCheck
    @GetMapping(value="")
    public PageList<SimpleItemDto> getItems(@Admin AdminUserEntity admin,
                                            PageRequestDto pageRequest) {
        log.info("getItems ::: {} {}", admin, pageRequest);

        return itemService.getItems(admin.getAdminNo(), pageRequest);
    }

    /**
     * 상품 상세 조회
     *
     * @author hjkim
     * @param itemSeq, admin
     */
    @AdminLoginCheck
    @GetMapping(value="/{itemSeq}")
    public DetailedItemDto getItem(@PathVariable long itemSeq,
                                   @Admin AdminUserEntity admin) {
        log.info("getItem ::: {} {}", itemSeq, admin);

        return itemService.getItem(itemSeq);
    }

    /**
     * 상품 상세 수정
     *
     * @author hjkim
     * @param itemSeq, admin
     */
    @AdminLoginCheck
    @PutMapping(value="/{itemSeq}")
    public void modifyItem(@PathVariable long itemSeq,
                           @Admin AdminUserEntity admin,
                           @RequestBody DetailedItemDto request) {
        log.info("modifyItem ::: {} {} {}", itemSeq, admin, request);

        itemService.modifyItem(itemSeq, request);
    }

    @AdminLoginCheck
    @DeleteMapping(value="/{itemSeq}")
    public void deleteItem(@PathVariable long itemSeq,
                           @Admin AdminUserEntity admin) {
        log.info("deleteItem ::: {} {}", itemSeq, admin);

        itemService.deleteItem(itemSeq);
    }

}