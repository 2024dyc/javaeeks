package com.smartshop.service;

import com.smartshop.entity.Category;
import com.smartshop.mapper.CategoryMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

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

    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    public int save(Category category) {
        if (category.getId() == null) {
            return categoryMapper.insert(category);
        } else {
            return categoryMapper.update(category);
        }
    }

    public int deleteById(Integer id) {
        return categoryMapper.deleteById(id);
    }

    public int getTotalProductCount() {
        return categoryMapper.getTotalProductCount();
    }
}