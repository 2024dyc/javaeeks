package com.smartshop.controller;

import com.github.pagehelper.PageInfo;
import com.smartshop.entity.Product;
import com.smartshop.service.CategoryService;
import com.smartshop.service.DashboardService;
import com.smartshop.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {

    @Resource
    private DashboardService dashboardService;

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"));
    }

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        if (isAdmin()) {
            Map<String, Object> summary = dashboardService.getSummary();
            model.addAttribute("summary", summary);

            List<Map<String, Object>> categoryPie = dashboardService.getCategoryPie();
            model.addAttribute("categoryPie", categoryPie);

            Map<String, Object> orderTrend = dashboardService.getOrderTrend();
            model.addAttribute("orderTrend", orderTrend);

            return "welcome";
        } else {
            return "redirect:/user-home";
        }
    }

    @GetMapping("/user-home")
    public String userHome(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        
        PageInfo<Product> hotProducts = productService.findByCondition(null, null, null, null, 1, 8);
        model.addAttribute("hotProducts", hotProducts.getList());
        
        PageInfo<Product> newProducts = productService.findByCondition(null, null, null, null, 1, 8);
        model.addAttribute("newProducts", newProducts.getList());

        return "user-home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
