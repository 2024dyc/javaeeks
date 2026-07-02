package com.smartshop.mapper;

import com.smartshop.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    List<CartItem> findByUserId(@Param("userId") Integer userId);

    CartItem findByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int insert(CartItem cartItem);

    int updateQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);

    int deleteById(@Param("id") Integer id);

    int deleteByUserId(@Param("userId") Integer userId);

    int deleteByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
}
