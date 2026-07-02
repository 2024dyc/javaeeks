-- ==================== 智慧商品管理系统 - 初始化数据 ====================

-- ==================== 1. 分类数据 ====================
INSERT IGNORE INTO `category` (`id`, `cat_name`, `create_time`, `update_time`) VALUES
(1, '电子数码', NOW(), NOW()),
(2, '服装鞋帽', NOW(), NOW()),
(3, '食品饮料', NOW(), NOW()),
(4, '图书音像', NOW(), NOW());

-- ==================== 2. 商品数据 ====================
INSERT IGNORE INTO `product` (`id`, `product_name`, `price`, `stock`, `description`, `cat_id`, `image`, `create_time`, `update_time`) VALUES
(1, 'iPhone 15', 6999.00, 50, 'Apple 最新款智能手机，A16芯片', 1, 'https://via.placeholder.com/300x300?text=iPhone15', NOW(), NOW()),
(2, 'MacBook Pro', 12999.00, 30, 'Apple M3 Pro芯片，14英寸', 1, 'https://via.placeholder.com/300x300?text=MacBook', NOW(), NOW()),
(3, '牛仔裤', 299.00, 200, '经典修身款牛仔裤，百搭时尚', 2, 'https://via.placeholder.com/300x300?text=Jeans', NOW(), NOW()),
(4, '纯棉T恤', 99.00, 500, '100%纯棉，舒适透气', 2, 'https://via.placeholder.com/300x300?text=TShirt', NOW(), NOW()),
(5, '薯片', 8.50, 1000, '原味薯片，香脆可口', 3, 'https://via.placeholder.com/300x300?text=Chips', NOW(), NOW()),
(6, 'Java编程思想', 79.00, 80, 'Java经典入门书籍，第4版', 4, 'https://via.placeholder.com/300x300?text=Java', NOW(), NOW()),
(7, 'AirPods Pro', 1899.00, 60, '主动降噪无线耳机', 1, 'https://via.placeholder.com/300x300?text=AirPods', NOW(), NOW()),
(8, '运动鞋', 599.00, 150, '轻便透气跑步鞋', 2, 'https://via.placeholder.com/300x300?text=Shoes', NOW(), NOW()),
(9, '可口可乐', 3.50, 2000, '330ml罐装', 3, 'https://via.placeholder.com/300x300?text=Cola', NOW(), NOW()),
(10, '算法导论', 108.00, 45, '计算机经典教材第三版', 4, 'https://via.placeholder.com/300x300?text=Algo', NOW(), NOW());

-- ==================== 3. 订单表
CREATE TABLE IF NOT EXISTS `orders` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号',
    `user_id` INT NOT NULL COMMENT '下单用户',
    `total_amount` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '总金额',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0待支付 1已支付 2已发货 3已完成 4已取消',
    `create_time` DATETIME DEFAULT NOW(),
    `update_time` DATETIME DEFAULT NOW(),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. 订单明细表
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_id` INT NOT NULL COMMENT '订单ID',
    `product_id` INT NOT NULL COMMENT '商品ID',
    `price` DECIMAL(10,2) NOT NULL COMMENT '下单时单价',
    `quantity` INT NOT NULL DEFAULT 1 COMMENT '数量',
    `subtotal` DECIMAL(10,2) NOT NULL COMMENT '小计',
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 测试数据：订单
INSERT INTO `orders` (`order_no`, `user_id`, `total_amount`, `status`, `create_time`, `update_time`) VALUES
('ORD20260701001', 1, 8297.00, 3, '2026-06-25 10:30:00', '2026-07-01 12:00:00'),
('ORD20260701002', 2, 398.00, 2, '2026-06-28 14:20:00', '2026-06-29 09:00:00'),
('ORD20260701003', 1, 87.50, 1, '2026-07-01 08:00:00', '2026-07-01 08:05:00'),
('ORD20260701004', 2, 6999.00, 0, '2026-07-01 11:00:00', '2026-07-01 11:00:00'),
('ORD20260701005', 1, 20798.00, 4, '2026-06-20 16:00:00', '2026-06-21 10:00:00'),
('ORD20260701006', 2, 179.00, 3, '2026-06-29 09:15:00', '2026-07-01 12:00:00'),
('ORD20260701007', 1, 12999.00, 1, '2026-06-30 13:00:00', '2026-06-30 13:10:00'),
('ORD20260701008', 2, 8.50, 2, '2026-07-01 07:30:00', '2026-07-01 08:00:00');

-- 4. 测试数据：订单明细
INSERT INTO `order_item` (`order_id`, `product_id`, `price`, `quantity`, `subtotal`) VALUES
(1, 1, 6999.00, 1, 6999.00),   -- iPhone 15
(1, 3, 299.00, 2, 598.00),      -- 牛仔裤 x2
(1, 4, 99.00, 5, 495.00),       -- T恤 x5
(1, 5, 8.50, 1, 8.50),          -- 薯片
(1, 6, 79.00, 1, 79.00),        -- Java编程思想
(2, 3, 299.00, 1, 299.00),      -- 牛仔裤
(2, 4, 99.00, 1, 99.00),        -- T恤
(3, 5, 8.50, 10, 85.00),        -- 薯片 x10
(3, 6, 79.00, 1, 79.00),        -- Java编程思想
(4, 1, 6999.00, 1, 6999.00),   -- iPhone 15
(5, 1, 6999.00, 2, 13998.00),   -- iPhone 15 x2
(5, 3, 299.00, 2, 598.00),      -- 牛仔裤 x2
(5, 2, 12999.00, 1, 12999.00),  -- MacBook Pro
(6, 4, 99.00, 1, 99.00),       -- T恤
(6, 6, 79.00, 1, 79.00),       -- Java编程思想
(7, 2, 12999.00, 1, 12999.00), -- MacBook Pro
(8, 5, 8.50, 1, 8.50);         -- 薯片
