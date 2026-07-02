package com.smartshop.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 订单明细实体
 */
@Data
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;

    /** 多表联查时存放商品名称 */
    private String productName;

    /** 多表联查时存放商品图片 */
    private String productImage;
}
