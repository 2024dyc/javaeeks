package com.smartshop.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 */
@Data
public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Integer categoryId;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /** 多表联查时存放分类名称（非数据库字段） */
    private String categoryName;
}