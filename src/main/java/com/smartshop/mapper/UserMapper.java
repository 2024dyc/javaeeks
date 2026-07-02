package com.smartshop.mapper;

import com.smartshop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     */
    User findByUsername(@Param("username") String username);

    /**
     * 查询所有用户
     */
    List<User> findAll();

    /**
     * 根据 ID 查询用户
     */
    User findById(@Param("id") Integer id);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 删除用户
     */
    int deleteById(@Param("id") Integer id);
}