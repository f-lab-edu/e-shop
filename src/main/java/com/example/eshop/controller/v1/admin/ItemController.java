package com.example.eshop.controller.v1.admin;

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
     * @param itemSeq, item
     */
    @PostMapping(value="/{itemSeq}")
    public void createItem(@PathVariable long itemSeq,
                           @RequestBody ItemDto item) {
        log.info("createItem ::: {} {}", itemSeq, item);

        // TODO : validate createItem request param

        itemService.createItem(itemSeq, item);
    }

}