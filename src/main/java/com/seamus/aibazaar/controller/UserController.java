package com.seamus.aibazaar.controller;

import com.seamus.aibazaar.entity.User;
import com.seamus.aibazaar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }
}
