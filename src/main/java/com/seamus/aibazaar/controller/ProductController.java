package com.seamus.aibazaar.controller;

import com.seamus.aibazaar.entity.Product;
import com.seamus.aibazaar.entity.ProductOrder;
import com.seamus.aibazaar.entity.User;
import com.seamus.aibazaar.repository.OrderRepository;
import com.seamus.aibazaar.repository.ProductRepository;
import com.seamus.aibazaar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

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

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("currentTab", "products");
        return "product/add";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products/";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("currentTab", "products");
        model.addAttribute("product", product);
        return "product/view";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        model.addAttribute("currentTab", "products");
        if (!Boolean.TRUE.equals(session.getAttribute("loggedInAdmin"))) {
            return "redirect:/products/";
        }
        productService.deleteProduct(id);
        return "redirect:/products/";
    }

    //CART METHODS

    @GetMapping("/add-to-cart/trained/{id}")
    public String addToCartTrained(@PathVariable Long id, HttpSession session) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null) {
            ProductOrder order = new ProductOrder();
            order.setName(product.getName());
            order.setPrice(product.getTrainedPrice());
            order.setTrained(true);
            order.setProduct(product);
            addOrderToCart(order, session);
        }
        return "redirect:/products/";
    }

    @GetMapping("/add-to-cart/untrained/{id}")
    public String addToCartUntrained(@PathVariable Long id, HttpSession session) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null) {
            ProductOrder order = new ProductOrder();
            order.setName(product.getName());
            order.setPrice(product.getUntrainedPrice());
            order.setTrained(false);
            order.setProduct(product);
            addOrderToCart(order, session);
        }
        return "redirect:/products/";
    }

    private void addOrderToCart(ProductOrder order, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        order.setUser(user);
        List<ProductOrder> cart = (List<ProductOrder>) session.getAttribute("cart");
        if(cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        cart.add(order);
    }


}
