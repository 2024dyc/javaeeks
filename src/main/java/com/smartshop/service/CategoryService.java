package com.smartshop.service;

import com.smartshop.entity.Category;
import com.smartshop.mapper.CategoryMapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有分类（含商品数量）
     * 缓存 Key: category::all
     */
    @Cacheable(value = "category", key = "'all'", unless = "#result == null")
    public List<Category> findAll() {
        List<Category> categories = categoryMapper.findAll();
        for (Category c : categories) {
            c.setProductCount(categoryMapper.getProductCountByCategoryId(c.getId()));
        }
        return categories;
    }

    public List<Category> findByName(String keyword) {
        List<Category> categories = categoryMapper.findByName(keyword);
        for (Category c : categories) {
            c.setProductCount(categoryMapper.getProductCountByCategoryId(c.getId()));
        }
        return categories;
    }

    /**
     * 根据 ID 查询分类
     * 缓存 Key: category::id
     */
    @Cacheable(value = "category", key = "#id", unless = "#result == null")
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    /**
     * 新增或更新分类 — 清除所有分类缓存
     */
    @CacheEvict(value = "category", allEntries = true)
    public int save(Category category) {
        if (category.getId() == null) {
            return categoryMapper.insert(category);
        } else {
            return categoryMapper.update(category);
        }
    }

    /**
     * 删除分类 — 清除所有分类缓存
     */
    @CacheEvict(value = "category", allEntries = true)
    public int deleteById(Integer id) {
        return categoryMapper.deleteById(id);
    }

    public int getTotalProductCount() {
        return categoryMapper.getTotalProductCount();
    }
}