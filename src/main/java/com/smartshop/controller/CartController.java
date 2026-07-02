package com.smartshop.controller;

import com.smartshop.entity.CartItem;
import com.smartshop.entity.User;
import com.smartshop.service.CartService;
import com.smartshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private UserService userService;

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        return user != null ? user.getId() : null;
    }

    @GetMapping
    public String list(Model model) {
        Integer userId = getCurrentUserId();
        List<CartItem> items = cartService.findByUserId(userId);
        model.addAttribute("items", items);
        model.addAttribute("totalCount", cartService.getTotalCount(userId));
        model.addAttribute("totalPrice", cartService.getTotalPrice(userId));
        return "cart/list";
    }

    @PostMapping("/add")
    public String add(@RequestParam Integer productId, @RequestParam(defaultValue = "1") Integer quantity,
                      RedirectAttributes ra) {
        Integer userId = getCurrentUserId();
        try {
            cartService.addToCart(userId, productId, quantity);
            ra.addFlashAttribute("msg", "添加购物车成功");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/product";
    }

    @GetMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam Integer quantity,
                         RedirectAttributes ra) {
        try {
            cartService.updateQuantity(id, quantity);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes ra) {
        cartService.deleteById(id);
        ra.addFlashAttribute("msg", "删除成功");
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clear(RedirectAttributes ra) {
        Integer userId = getCurrentUserId();
        cartService.clearCart(userId);
        ra.addFlashAttribute("msg", "清空成功");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Integer userId = getCurrentUserId();
        List<CartItem> items = cartService.findByUserId(userId);
        if (items.isEmpty()) {
            model.addAttribute("error", "购物车为空");
            return "cart/list";
        }
        model.addAttribute("items", items);
        model.addAttribute("totalCount", cartService.getTotalCount(userId));
        model.addAttribute("totalPrice", cartService.getTotalPrice(userId));
        return "cart/checkout";
    }
}
