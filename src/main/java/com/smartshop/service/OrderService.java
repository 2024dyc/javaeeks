package com.smartshop.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartshop.entity.Order;
import com.smartshop.entity.OrderItem;
import com.smartshop.mapper.OrderMapper;
import com.smartshop.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    /** 动态条件分页查询 */
    public PageInfo<Order> findByCondition(String orderNo, Integer userId, Integer status,
                                           String startDate, String endDate,
                                           int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = orderMapper.findByCondition(orderNo, userId, status, startDate, endDate);
        return new PageInfo<>(list);
    }

    /** 根据 ID 查询订单 */
    public Order findById(Integer id) {
        Order order = orderMapper.findById(id);
        if (order != null) {
            List<OrderItem> items = orderItemMapper.findByOrderId(id);
            order.setItems(items);
        }
        return order;
    }

    /** 更新订单状态 */
    public int updateStatus(Integer id, Integer status) {
        return orderMapper.updateStatus(id, status);
    }

    /** 统计订单总数 */
    public int countAll() {
        return orderMapper.countAll();
    }

    /** 统计总销售额 */
    public Object getTotalAmount() {
        Map<String, Object> map = orderMapper.sumTotalAmount();
        return map != null ? map.get("total") : 0;
    }

    /** 近7天订单趋势 */
    public List<Map<String, Object>> countByDay(int days) {
        return orderMapper.countByDay(days);
    }
}
