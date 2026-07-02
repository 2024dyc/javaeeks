package com.smartshop.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartshop.entity.Product;
import com.smartshop.mapper.ProductMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    /**
     * 动态条件查询 + 分页
     * 缓存 Key: product::page::{keyword}_{categoryId}_{priceMin}_{priceMax}_{pageNum}_{pageSize}
     */
    public PageInfo<Product> findByCondition(String keyword, Integer categoryId,
                                             Double priceMin, Double priceMax,
                                             int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.findByCondition(keyword, categoryId, priceMin, priceMax);
        return new PageInfo<>(list);
    }

    /**
     * 根据 ID 查询单个商品
     */
    public Product findById(Integer id) {
        return productMapper.findById(id);
    }

    /**
     * 新增或更新商品 — 清除所有分页缓存
     */
    public int save(Product product) {
        if (product.getId() == null) {
            return productMapper.insert(product);
        } else {
            return productMapper.update(product);
        }
    }

    /**
     * 删除商品 — 清除 ID 缓存 + 分页缓存
     */
    public int deleteById(Integer id) {
        return productMapper.deleteById(id);
    }
}