package com.seamus.aibazaar.controller;

import com.seamus.aibazaar.entity.User;
import com.seamus.aibazaar.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.AdviceKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("currentTab", "login");
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("currentTab", "signup");
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        if ("admin".equals(user.getUsername())) return "redirect:/signup"; //don't allow creation of admin accounts
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("currentTab", "login");
        return "user/login";
    }

    @PostMapping("/login")
    public String handleLoginRequest(@RequestParam("username") String username, @RequestParam("password") String password) {

        if ("admin".equals(username) && "bazaar123".equals(password)) {     //hardcoded check for admin login (not stored in database)
            session.setAttribute("loggedInAdmin", true);
            User admin = new User();
            admin.setUsername("Administrator");
            session.setAttribute("loggedInUser", admin);
            return "redirect:/products/";
        }

        User userFromDb = userService.findUserByUsernameAndPassword(username, password); //if not admin it then proceeds to search database for user

        if (userFromDb != null) { //if user exists redirect to products page
            session.setAttribute("loggedInAdmin", false);
            session.setAttribute("loggedInUser", userFromDb);
            return "redirect:/products/";
        }

        return "redirect:/login";   //otherwise remain on login page
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        session.setAttribute("loggedInAdmin", false);
        session.removeAttribute("loggedInUser");
        model.addAttribute("currentTab", "login");
        return "redirect:/login";
    }
}
