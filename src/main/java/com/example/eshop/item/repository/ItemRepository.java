package com.example.eshop.item.repository;

import com.example.eshop.common.dto.PageRequestDto;
import com.example.eshop.item.model.ItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ItemRepository {
    void insertItem(ItemEntity item);
    int getTotalCount(Long adminSeq);
    List<ItemEntity> selectItems(Long adminSeq, PageRequestDto pageRequest);
    ItemEntity selectItem(long itemSeq);
    void updateItem(ItemEntity item);
    void deleteItem(long itemSeq);
}
