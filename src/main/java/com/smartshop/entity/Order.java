package com.smartshop.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单实体
 */
@Data
public class Order {
    private Integer id;
    private String orderNo;
    private Integer userId;
    private BigDecimal totalAmount;
    private Integer status;          // 0待支付 1已支付 2已发货 3已完成 4已取消
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /** 多表联查时存放用户名 */
    private String username;

    /** 订单商品名称（多个商品用逗号分隔） */
    private String productNames;

    /** 订单明细列表 */
    private List<OrderItem> items;

    public String getStatusText() {
        switch (status == null ? 0 : status) {
            case 0: return "待支付";
            case 1: return "已支付";
            case 2: return "已发货";
            case 3: return "已完成";
            case 4: return "已取消";
            default: return "未知";
        }
    }

    public String getStatusBadge() {
        switch (status == null ? 0 : status) {
            case 0: return "warning";
            case 1: return "info";
            case 2: return "primary";
            case 3: return "success";
            case 4: return "secondary";
            default: return "dark";
        }
    }
}
