package com.deploy6.deploy6.controller;

import com.deploy6.deploy6.model.User;
import com.deploy6.deploy6.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "12345678";

    @Autowired
    private UserRepository userRepository;

    // ===================== LOGIN =====================

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            session.setAttribute("loggedIn", true);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau password salah!");
            return "login";
        }
    }

    // ===================== HOME =====================

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("userList", userRepository.findAll());
        return "home";
    }

    // ===================== FORM =====================

    @GetMapping("/form")
    public String formPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute User user, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        userRepository.save(user);
        return "redirect:/home";
    }

    // ===================== LOGOUT =====================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}