package com.example.eshop.admin.category.repository;

import com.example.eshop.admin.category.model.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryRepository {
    CategoryEntity findCategoryByCategoryNo(long categoryNo);
}
