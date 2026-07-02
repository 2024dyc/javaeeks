package com.smartshop.controller;

import com.smartshop.entity.User;
import com.smartshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("user", userService.findById(id));
        } else {
            model.addAttribute("user", new User());
        }
        return "user/form";
    }

    @PostMapping("/save")
    public String save(User user, RedirectAttributes ra) {
        userService.save(user);
        ra.addFlashAttribute("msg", "保存成功");
        return "redirect:/user";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes ra) {
        userService.deleteById(id);
        ra.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }

    @GetMapping("/toggleEnabled")
    public String toggleEnabled(@RequestParam Integer id, @RequestParam Integer enabled, RedirectAttributes ra) {
        userService.updateEnabled(id, enabled);
        ra.addFlashAttribute("msg", enabled == 1 ? "用户已启用" : "用户已禁用");
        return "redirect:/user";
    }
}
