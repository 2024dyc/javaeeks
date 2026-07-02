package com.smartshop;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("admin / admin123 → " + encoder.encode("admin123"));
        System.out.println("user  / user123  → " + encoder.encode("user123"));
    }
}