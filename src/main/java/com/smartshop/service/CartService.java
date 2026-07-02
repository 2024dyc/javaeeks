package com.smartshop.service;

import com.smartshop.entity.CartItem;
import com.smartshop.entity.Product;
import com.smartshop.mapper.CartMapper;
import com.smartshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    public List<CartItem> findByUserId(Integer userId) {
        return cartMapper.findByUserId(userId);
    }

    public int addToCart(Integer userId, Integer productId, Integer quantity) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }

        CartItem existing = cartMapper.findByUserIdAndProductId(userId, productId);
        if (existing != null) {
            int newQuantity = existing.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                throw new RuntimeException("库存不足");
            }
            return cartMapper.updateQuantity(existing.getId(), newQuantity);
        }

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setProductName(product.getName());
        cartItem.setProductImage(product.getImage());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        return cartMapper.insert(cartItem);
    }

    public int updateQuantity(Integer id, Integer quantity) {
        return cartMapper.updateQuantity(id, quantity);
    }

    public int deleteById(Integer id) {
        return cartMapper.deleteById(id);
    }

    public int deleteByUserIdAndProductId(Integer userId, Integer productId) {
        return cartMapper.deleteByUserIdAndProductId(userId, productId);
    }

    public int clearCart(Integer userId) {
        return cartMapper.deleteByUserId(userId);
    }

    public BigDecimal getTotalPrice(Integer userId) {
        List<CartItem> items = cartMapper.findByUserId(userId);
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalCount(Integer userId) {
        List<CartItem> items = cartMapper.findByUserId(userId);
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
