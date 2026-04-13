package com.deploy6.deploy6.controller;


import com.deploy6.deploy6.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "12345678"; // Ganti dengan NIM kamu

    // Simpan data sementara (temporary, tidak ke database)
    private List<User> userList = new ArrayList<>();

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
        model.addAttribute("userList", userList);
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
        userList.add(user);
        return "redirect:/home";
    }

    // ===================== LOGOUT =====================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}