package com.seamus.aibazaar.controller;

import com.seamus.aibazaar.entity.Product;
import com.seamus.aibazaar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String listProducts(Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            model.addAttribute("currentTab", "login");
            return "redirect:/login";
        }
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("currentTab", "products");
        return "product/list";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("currentTab", "products");
        model.addAttribute("product", product);
        return "product/view";
    }

}
