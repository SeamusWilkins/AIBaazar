package com.seamus.aibazaar.controller;

import com.seamus.aibazaar.entity.ProductOrder;
import com.seamus.aibazaar.entity.User;
import com.seamus.aibazaar.repository.OrderRepository;
import com.seamus.aibazaar.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String listOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        List<ProductOrder> orders;

        if(Boolean.TRUE.equals(session.getAttribute("loggedInAdmin"))) {
            orders = orderService.findAllOrders();
            model.addAttribute("loggedInAdmin", true);
        } else {
            orders = orderService.findAllByUser(user);
            model.addAttribute("loggedInAdmin", false);
        }

        model.addAttribute("currentTab", "orders");
        model.addAttribute("orders", orders);
        return "order/list";
    }


    @PostMapping("/confirm-checkout")
    public String confirmCheckout(HttpSession session) {
        List<ProductOrder> cart = (List<ProductOrder>) session.getAttribute("cart");
        if(cart != null) {
            orderService.saveAll(cart);
            session.removeAttribute("cart");
        }
        return "redirect:/orders/";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        model.addAttribute("cart", session.getAttribute("cart"));
        model.addAttribute("currentTab", "cart");
        return "order/checkout";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        model.addAttribute("cart", session.getAttribute("cart"));
        model.addAttribute("currentTab", "cart");
        return "order/cart";
    }
}

