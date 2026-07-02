package com.smartshop.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 系统用户实体
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;         // ROLE_admin / ROLE_user
    private Integer enabled;     // 1=启用 0=禁用
    private LocalDateTime createTime;
}