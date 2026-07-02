package com.smartshop.mapper;

import com.smartshop.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT c.id, c.name, c.color, c.create_time, c.update_time " +
            "FROM category c ORDER BY c.id ASC")
    List<Category> findAll();

    @Select("SELECT c.id, c.name, c.color, c.create_time, c.update_time " +
            "FROM category c WHERE c.name LIKE CONCAT('%', #{keyword}, '%') ORDER BY c.id ASC")
    List<Category> findByName(@Param("keyword") String keyword);

    @Select("SELECT id, name, color, create_time, update_time FROM category WHERE id = #{id}")
    Category findById(@Param("id") Integer id);

    @Insert("INSERT INTO category(name, color, create_time, update_time) VALUES(#{name}, #{color}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    @Update("UPDATE category SET name=#{name}, color=#{color}, update_time=NOW() WHERE id=#{id}")
    int update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM product")
    int getTotalProductCount();

    @Select("SELECT COUNT(*) FROM product WHERE category_id = #{categoryId}")
    int getProductCountByCategoryId(@Param("categoryId") Integer categoryId);
}