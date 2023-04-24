package com.example.eshop.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DateInfoEntity {
    private LocalDateTime regDt;
    private LocalDateTime updDt;
}
