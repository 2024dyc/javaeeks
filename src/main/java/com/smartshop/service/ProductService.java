package com.smartshop.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartshop.entity.Product;
import com.smartshop.mapper.ProductMapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    /**
     * 动态条件查询 + 分页
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
     * 缓存 Key: product::id
     */
    @Cacheable(value = "product", key = "#id", unless = "#result == null")
    public Product findById(Integer id) {
        return productMapper.findById(id);
    }

    /**
     * 新增或更新商品 — 清除所有商品缓存
     */
    @CacheEvict(value = "product", allEntries = true)
    public int save(Product product) {
        if (product.getId() == null) {
            return productMapper.insert(product);
        } else {
            return productMapper.update(product);
        }
    }

    /**
     * 删除商品 — 清除所有商品缓存
     */
    @CacheEvict(value = "product", allEntries = true)
    public int deleteById(Integer id) {
        return productMapper.deleteById(id);
    }
}