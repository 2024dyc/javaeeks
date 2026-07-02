package com.smartshop.mapper;

import com.smartshop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /** 动态条件分页查询订单 */
    List<Order> findByCondition(@Param("orderNo") String orderNo,
                                @Param("userId") Integer userId,
                                @Param("status") Integer status,
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate);

    /** 根据 ID 查询订单（含用户名） */
    Order findById(@Param("id") Integer id);

    /** 更新订单状态 */
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    /** 统计订单总数 */
    int countAll();

    /** 统计总销售额 */
    Map<String, Object> sumTotalAmount();

    /** 近7天每日订单数 */
    List<Map<String, Object>> countByDay(@Param("days") int days);
}
