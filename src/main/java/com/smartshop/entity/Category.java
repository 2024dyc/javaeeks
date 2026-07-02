package com.smartshop.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品分类实体
 */
@Data
public class Category {
    private Integer id;
    private String name;
    private Integer color;
    private Integer productCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}