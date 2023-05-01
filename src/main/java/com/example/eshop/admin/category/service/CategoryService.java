package com.example.eshop.admin.category.service;

import com.example.eshop.admin.category.model.CategoryEntity;

public interface CategoryService {
    CategoryEntity getCategoryByCategoryNo(long categoryNo);
}
