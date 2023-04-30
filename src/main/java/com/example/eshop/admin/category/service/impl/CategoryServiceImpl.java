package com.example.eshop.admin.category.service.impl;

import com.example.eshop.admin.category.model.CategoryEntity;
import com.example.eshop.admin.category.repository.CategoryRepository;
import com.example.eshop.admin.category.service.CategoryService;
import com.example.eshop.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryEntity getCategoryByCategoryNo(long categoryNo) {
        log.info("getCategoryByCategoryNo ::: {}", categoryNo);

        CategoryEntity category = categoryRepository.findCategoryByCategoryNo(categoryNo);
        checkCategoryExist(category);

        return category;
    }

    private void checkCategoryExist(CategoryEntity category) {
        if (category == null) {
            throw new DataNotFoundException();
        }
    }
}
