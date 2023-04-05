package com.example.eshop.item.repository;

import com.example.eshop.item.model.ItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ItemRepository {
    void insertItem(ItemEntity item);
}
