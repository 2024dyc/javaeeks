package com.smartshop.controller;

import com.github.pagehelper.PageInfo;
import com.smartshop.aspect.CacheMonitorAspect;
import com.smartshop.entity.Product;
import com.smartshop.service.CategoryService;
import com.smartshop.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @Value("${upload.path:uploads}")
    private String uploadPath;

    /**
     * 商品列表页（动态搜索 + 分页 + 缓存状态）
     */
    @GetMapping
    public String list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer categoryId,
                       @RequestParam(required = false) Double priceMin,
                       @RequestParam(required = false) Double priceMax,
                       @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize,
                       Model model) {
        PageInfo<Product> pageInfo = productService.findByCondition(
                keyword, categoryId, priceMin, priceMax, pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("categories", categoryService.findAll());
        // 条件回显
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("priceMin", priceMin);
        model.addAttribute("priceMax", priceMax);
        // 缓存来源信息
        model.addAttribute("cacheSource", CacheMonitorAspect.dataSource.get());
        model.addAttribute("cacheTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        CacheMonitorAspect.clear();
        return "product/list";
    }

    /**
     * 新增/编辑表单页
     */
    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("product", productService.findById(id));
        } else {
            model.addAttribute("product", new Product());
        }
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    /**
     * 保存商品（新增或更新）
     */
    @PostMapping("/save")
    public String save(Product product, RedirectAttributes ra) {
        productService.save(product);
        ra.addFlashAttribute("msg", "保存成功");
        return "redirect:/product";
    }

    /**
     * 上传商品图片
     */
    @PostMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "请选择文件");
            return result;
        }

        // 检查文件类型
        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        if (!ext.matches("\\.(jpg|jpeg|png|gif|bmp|webp)$")) {
            result.put("success", false);
            result.put("message", "不支持的文件格式，仅支持 jpg/png/gif/bmp/webp");
            return result;
        }

        try {
            // 生成唯一文件名
            String newFileName = UUID.randomUUID().toString() + ext;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File destFile = new File(uploadDir, newFileName);
            file.transferTo(destFile);

            String imageUrl = "/uploads/" + newFileName;
            result.put("success", true);
            result.put("url", imageUrl);
            result.put("message", "上传成功");
        } catch (IOException e) {
            result.put("success", false);
            result.put("message", "上传失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 删除商品
     */
    @GetMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes ra) {
        productService.deleteById(id);
        ra.addFlashAttribute("msg", "删除成功");
        return "redirect:/product";
    }
}