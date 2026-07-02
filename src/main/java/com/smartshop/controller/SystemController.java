package com.smartshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/system")
public class SystemController {

    @GetMapping("/settings")
    public String settings() {
        return "system/settings";
    }

    @PostMapping("/updateSettings")
    public String updateSettings(RedirectAttributes ra) {
        ra.addFlashAttribute("msg", "设置保存成功");
        return "redirect:/system/settings";
    }
}
