package com.example.eshop.admin.category.model;

import com.example.eshop.common.model.DateInfoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CategoryEntity extends DateInfoEntity {
    private long categoryNo;

    private Long parentCategoryNo;

    @Setter
    private String categoryNm;

    public CategoryEntity(String name) {
        this.categoryNm = name;
    }

    public CategoryEntity(Long parentCategoryNo, String name) {
        this.parentCategoryNo = parentCategoryNo;
        this.categoryNm = name;
    }
}
