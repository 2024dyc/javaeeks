package com.smartshop.service;

import com.smartshop.entity.User;
import com.smartshop.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public int save(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(1);
            return userMapper.insert(user);
        } else {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return userMapper.update(user);
        }
    }

    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    public void updateEnabled(Integer id, Integer enabled) {
        User user = findById(id);
        if (user != null) {
            user.setEnabled(enabled);
            userMapper.update(user);
        }
    }
}
