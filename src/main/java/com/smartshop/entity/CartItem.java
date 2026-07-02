package com.smartshop.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartItem {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private Integer stock;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
