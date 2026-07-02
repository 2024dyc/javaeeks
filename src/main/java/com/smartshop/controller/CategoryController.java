package com.smartshop.controller;

import com.smartshop.entity.Category;
import com.smartshop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    public String list(Model model, @RequestParam(required = false) String keyword) {
        List<Category> categories;
        if (keyword != null && !keyword.trim().isEmpty()) {
            categories = categoryService.findByName(keyword.trim());
            model.addAttribute("keyword", keyword.trim());
        } else {
            categories = categoryService.findAll();
        }
        
        int totalProducts = categoryService.getTotalProductCount();
        int avgProducts = categories.isEmpty() ? 0 : totalProducts / categories.size();
        
        String topCategoryName = null;
        int maxCount = 0;
        for (Category c : categories) {
            if (c.getProductCount() != null && c.getProductCount() > maxCount) {
                maxCount = c.getProductCount();
                topCategoryName = c.getName();
            }
        }
        
        model.addAttribute("categories", categories);
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("avgProducts", avgProducts);
        model.addAttribute("topCategoryName", topCategoryName);
        return "category/list";
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(Category category) {
        if (category.getColor() == null) {
            category.setColor(1);
        }
        categoryService.save(category);
        return "ok";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes ra) {
        categoryService.deleteById(id);
        ra.addFlashAttribute("msg", "删除成功");
        return "redirect:/category";
    }
}