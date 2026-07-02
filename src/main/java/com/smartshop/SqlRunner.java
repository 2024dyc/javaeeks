package com.smartshop;

import java.sql.*;

public class SqlRunner {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://129.204.142.57:3306/0424dcy?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
        String user = "root";
        String pass = "Bigdata22!";

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
            System.out.println("Connected: " + conn.getCatalog());

            String[] sqls = {
                // ========== 商品分类 ==========
                "INSERT IGNORE INTO category (id, cat_name, create_time, update_time) VALUES (1, '电子数码', NOW(), NOW())",
                "INSERT IGNORE INTO category (id, cat_name, create_time, update_time) VALUES (2, '服装鞋帽', NOW(), NOW())",
                "INSERT IGNORE INTO category (id, cat_name, create_time, update_time) VALUES (3, '食品饮料', NOW(), NOW())",
                "INSERT IGNORE INTO category (id, cat_name, create_time, update_time) VALUES (4, '图书音像', NOW(), NOW())",
                // ========== 商品 ==========
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (1, 'iPhone 15', 6999.00, 50, 'Apple 最新款智能手机，A16芯片', 1, 'https://via.placeholder.com/300x300?text=iPhone15', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (2, 'MacBook Pro', 12999.00, 30, 'Apple M3 Pro芯片，14英寸', 1, 'https://via.placeholder.com/300x300?text=MacBook', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (3, '牛仔裤', 299.00, 200, '经典修身款牛仔裤，百搭时尚', 2, 'https://via.placeholder.com/300x300?text=Jeans', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (4, '纯棉T恤', 99.00, 500, '100%纯棉，舒适透气', 2, 'https://via.placeholder.com/300x300?text=TShirt', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (5, '薯片', 8.50, 1000, '原味薯片，香脆可口', 3, 'https://via.placeholder.com/300x300?text=Chips', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (6, 'Java编程思想', 79.00, 80, 'Java经典入门书籍，第4版', 4, 'https://via.placeholder.com/300x300?text=Java', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (7, 'AirPods Pro', 1899.00, 60, '主动降噪无线耳机', 1, 'https://via.placeholder.com/300x300?text=AirPods', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (8, '运动鞋', 599.00, 150, '轻便透气跑步鞋', 2, 'https://via.placeholder.com/300x300?text=Shoes', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (9, '可口可乐', 3.50, 2000, '330ml罐装', 3, 'https://via.placeholder.com/300x300?text=Cola', NOW(), NOW())",
                "INSERT IGNORE INTO product (id, product_name, price, stock, description, cat_id, image, create_time, update_time) VALUES (10, '算法导论', 108.00, 45, '计算机经典教材第三版', 4, 'https://via.placeholder.com/300x300?text=Algo', NOW(), NOW())",
                // ========== 订单表 ==========
                "CREATE TABLE IF NOT EXISTS orders (id INT PRIMARY KEY AUTO_INCREMENT, order_no VARCHAR(32) NOT NULL UNIQUE, user_id INT NOT NULL, total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00, status TINYINT NOT NULL DEFAULT 0, create_time DATETIME DEFAULT NOW(), update_time DATETIME DEFAULT NOW()) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
                // order_item table
                "CREATE TABLE IF NOT EXISTS order_item (id INT PRIMARY KEY AUTO_INCREMENT, order_id INT NOT NULL, product_id INT NOT NULL, price DECIMAL(10,2) NOT NULL, quantity INT NOT NULL DEFAULT 1, subtotal DECIMAL(10,2) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
                // Insert orders
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701001', 1, 8297.00, 3, '2026-06-25 10:30:00', '2026-07-01 12:00:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701002', 2, 398.00, 2, '2026-06-28 14:20:00', '2026-06-29 09:00:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701003', 1, 87.50, 1, '2026-07-01 08:00:00', '2026-07-01 08:05:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701004', 2, 6999.00, 0, '2026-07-01 11:00:00', '2026-07-01 11:00:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701005', 1, 20798.00, 4, '2026-06-20 16:00:00', '2026-06-21 10:00:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701006', 2, 179.00, 3, '2026-06-29 09:15:00', '2026-07-01 12:00:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701007', 1, 12999.00, 1, '2026-06-30 13:00:00', '2026-06-30 13:10:00')",
                "INSERT INTO orders (order_no, user_id, total_amount, status, create_time, update_time) VALUES ('ORD20260701008', 2, 8.50, 2, '2026-07-01 07:30:00', '2026-07-01 08:00:00')",
                // 4. Insert order items
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (1, 1, 6999.00, 1, 6999.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (1, 3, 299.00, 2, 598.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (1, 4, 99.00, 5, 495.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (1, 5, 8.50, 1, 8.50)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (1, 6, 79.00, 1, 79.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (2, 3, 299.00, 1, 299.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (2, 4, 99.00, 1, 99.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (3, 5, 8.50, 10, 85.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (3, 6, 79.00, 1, 79.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (4, 1, 6999.00, 1, 6999.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (5, 1, 6999.00, 2, 13998.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (5, 3, 299.00, 2, 598.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (5, 2, 12999.00, 1, 12999.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (6, 4, 99.00, 1, 99.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (6, 6, 79.00, 1, 79.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (7, 2, 12999.00, 1, 12999.00)",
                "INSERT INTO order_item (order_id, product_id, price, quantity, subtotal) VALUES (8, 5, 8.50, 1, 8.50)"
            };

            int ok = 0, fail = 0;
            for (String sql : sqls) {
                try {
                    stmt.execute(sql);
                    ok++;
                    String shortSql = sql.length() > 80 ? sql.substring(0, 80) + "..." : sql;
                    System.out.println("  [OK] " + shortSql);
                } catch (SQLException e) {
                    fail++;
                    System.out.println("  [FAIL] " + e.getMessage());
                }
            }
            System.out.println("\nDone: " + ok + " success, " + fail + " fail");
        }
    }
}
