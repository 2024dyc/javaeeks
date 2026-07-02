package com.smartshop.controller;

import com.github.pagehelper.PageInfo;
import com.smartshop.entity.Order;
import com.smartshop.entity.User;
import com.smartshop.service.OrderService;
import com.smartshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        return user != null ? user.getId() : null;
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"));
    }

    /** 订单列表 */
    @GetMapping
    public String list(@RequestParam(required = false) String orderNo,
                       @RequestParam(required = false) Integer userId,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize,
                       Model model) {
        try {
            Integer currentUserId = getCurrentUserId();
            Integer queryUserId = isAdmin() ? userId : currentUserId;
            
            PageInfo<Order> pageInfo = orderService.findByCondition(
                    orderNo, queryUserId, status, startDate, endDate, pageNum, pageSize);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("isAdmin", isAdmin());
        } catch (Exception e) {
            model.addAttribute("pageInfo", null);
            model.addAttribute("msg", "订单表尚未创建，请先执行 sql/init.sql");
        }
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("userId", userId);
        model.addAttribute("status", status);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "order/list";
    }

    /** 订单详情 */
    @GetMapping("/detail")
    public String detail(@RequestParam Integer id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("isAdmin", isAdmin());
        return "order/detail";
    }

    /** 更新订单状态 */
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Integer id, @RequestParam Integer status,
                               RedirectAttributes ra) {
        orderService.updateStatus(id, status);
        ra.addFlashAttribute("msg", "状态更新成功");
        return "redirect:/order";
    }
}
