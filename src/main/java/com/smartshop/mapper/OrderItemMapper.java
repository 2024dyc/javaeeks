package com.smartshop.mapper;

import com.smartshop.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    /** 根据订单 ID 查询明细（含商品名） */
    List<OrderItem> findByOrderId(@Param("orderId") Integer orderId);
}
