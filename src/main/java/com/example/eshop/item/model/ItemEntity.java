package com.example.eshop.item.model;

import com.example.eshop.common.model.DateInfoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ItemEntity extends DateInfoEntity {
    private long itemNo;
    private Long categoryNo;
    private long adminNo;

    @Setter
    private String name;

    @Setter
    private String status;

    @Setter
    private long remains;

    @Setter
    private long price;

    @Setter
    private String intro;

    @Setter
    private String bigImage;

    @Setter
    private String smallImage;

    @Setter
    private long cellCnt;

    @Setter
    private long likes;

    @Setter
    private String content;

    @Setter
    private String adYn;

    @Setter
    private String mdRecommendYn;

    @Setter
    private String fastYn;

    @Builder
    public ItemEntity(long adminNo, Long categoryNo, String name, String smallImage,
                      String bigImage, long price, String intro,
                      String content, long remains, String fastYn) {
        this.adminNo = adminNo;
        this.categoryNo = categoryNo;
        this.name = name;
        this.smallImage = smallImage;
        this.bigImage = bigImage;
        this.price = price;
        this.intro = intro;
        this.content = content;
        this.remains = remains;
        this.fastYn = fastYn;
    }
}
