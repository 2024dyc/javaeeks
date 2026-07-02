package com.smartshop.service;

import com.smartshop.entity.User;
import com.smartshop.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 从数据库查询用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在：" + username);
        }

        // 2. 将角色转换为 Spring Security 的 GrantedAuthority
        // 数据库存储格式：ROLE_admin / ROLE_user
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        // 3. 返回 Spring Security 的 UserDetails 对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled() == 1,    // enabled
                true,                       // accountNonExpired
                true,                       // credentialsNonExpired
                true,                       // accountNonLocked
                Collections.singletonList(authority)
        );
    }
}