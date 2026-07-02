package com.smartshop.mapper;

import com.smartshop.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品 Mapper — XML 版本，支持动态组合查询
 */
@Mapper
public interface ProductMapper {

    /**
     * 动态条件查询商品列表（含分类名称）
     */
    List<Product> findByCondition(@Param("keyword") String keyword,
                                  @Param("categoryId") Integer categoryId,
                                  @Param("priceMin") Double priceMin,
                                  @Param("priceMax") Double priceMax);

    /**
     * 根据 ID 查询单个商品（含分类名称）
     */
    Product findById(@Param("id") Integer id);

    /**
     * 新增商品
     */
    int insert(Product product);

    /**
     * 更新商品
     */
    int update(Product product);

    /**
     * 删除商品
     */
    int deleteById(@Param("id") Integer id);

    /** 统计商品总数 */
    int countAll();

    /** 按分类统计商品数 */
    List<Map<String, Object>> countByCategory();
}