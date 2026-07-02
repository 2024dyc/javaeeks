package com.smartshop.service;

import com.smartshop.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderService orderService;

    @Resource
    private CategoryService categoryService;

    /** 仪表板总览数据 */
    public Map<String, Object> getSummary() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("productCount", safeInt(() -> productMapper.countAll()));
        data.put("categoryCount", safeInt(() -> {
            try { return categoryService.findAll().size(); } catch (Exception e) { return 0; }
        }));
        data.put("orderCount", safeInt(() -> orderService.countAll()));
        data.put("totalAmount", safeDouble(() -> {
            Object result = orderService.getTotalAmount();
            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            }
            return 0.0;
        }));
        return data;
    }

    private double safeDouble(DoubleSupplier supplier) {
        try { return supplier.get(); } catch (Exception e) { return 0.0; }
    }

    @FunctionalInterface interface DoubleSupplier { double get(); }

    /** 商品分类占比（饼图数据） */
    public List<Map<String, Object>> getCategoryPie() {
        try {
            List<Map<String, Object>> raw = productMapper.countByCategory();
            List<Map<String, Object>> filtered = new ArrayList<>();
            for (Map<String, Object> row : raw) {
                Integer value = row.get("value") != null ? ((Number) row.get("value")).intValue() : 0;
                if (value > 0) {
                    filtered.add(row);
                }
            }
            if (filtered.isEmpty()) {
                Map<String, Object> empty = new LinkedHashMap<>();
                empty.put("name", "暂无数据");
                empty.put("value", 1);
                filtered.add(empty);
            }
            return filtered;
        } catch (Exception e) {
            log.warn("获取分类占比失败: {}", e.getMessage());
            List<Map<String, Object>> empty = new ArrayList<>();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("name", "暂无数据");
            item.put("value", 1);
            empty.add(item);
            return empty;
        }
    }

    /** 近7天订单趋势（折线图数据） */
    public Map<String, Object> getOrderTrend() {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            List<Map<String, Object>> raw = orderService.countByDay(7);
            
            Map<String, Long> dayCountMap = new LinkedHashMap<>();
            for (Map<String, Object> row : raw) {
                String day = String.valueOf(row.get("day"));
                Long cnt = row.get("cnt") != null ? ((Number) row.get("cnt")).longValue() : 0L;
                dayCountMap.put(day, cnt);
            }

            List<String> days = new ArrayList<>();
            List<Long> counts = new ArrayList<>();
            
            Calendar cal = Calendar.getInstance();
            for (int i = 6; i >= 0; i--) {
                Calendar temp = (Calendar) cal.clone();
                temp.add(Calendar.DAY_OF_MONTH, -i);
                String dateStr = String.format("%04d-%02d-%02d",
                        temp.get(Calendar.YEAR),
                        temp.get(Calendar.MONTH) + 1,
                        temp.get(Calendar.DAY_OF_MONTH));
                days.add(dateStr);
                counts.add(dayCountMap.getOrDefault(dateStr, 0L));
            }
            
            result.put("days", days);
            result.put("counts", counts);
        } catch (Exception e) {
            log.warn("获取订单趋势失败: {}", e.getMessage());
            List<String> days = new ArrayList<>();
            List<Long> counts = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            for (int i = 6; i >= 0; i--) {
                Calendar temp = (Calendar) cal.clone();
                temp.add(Calendar.DAY_OF_MONTH, -i);
                String dateStr = String.format("%04d-%02d-%02d",
                        temp.get(Calendar.YEAR),
                        temp.get(Calendar.MONTH) + 1,
                        temp.get(Calendar.DAY_OF_MONTH));
                days.add(dateStr);
                counts.add(0L);
            }
            result.put("days", days);
            result.put("counts", counts);
        }
        return result;
    }

    private int safeInt(IntSupplier supplier) {
        try { return supplier.get(); } catch (Exception e) { return 0; }
    }

    private Object safeObject(ObjSupplier supplier, Object fallback) {
        try { return supplier.get(); } catch (Exception e) { return fallback; }
    }

    @FunctionalInterface interface IntSupplier { int get(); }
    @FunctionalInterface interface ObjSupplier { Object get(); }
}
