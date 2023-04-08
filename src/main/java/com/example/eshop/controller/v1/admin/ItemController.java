package com.example.eshop.controller.v1.admin;

import com.example.eshop.admin.member.core.model.AdminUserEntity;
import com.example.eshop.aop.admin.Admin;
import com.example.eshop.aop.admin.AdminLoginCheck;
import com.example.eshop.controller.dto.ItemDto;
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
    public void createItem(@Admin AdminUserEntity admin,
                           @RequestBody ItemDto item) {
        log.info("createItem ::: {} {}", admin, item);

        itemService.createItem(admin.getAdminNo(), item);
    }

}