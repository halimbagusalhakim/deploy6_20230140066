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

    // Data mahasiswa disimpan sementara di session (temporary, tidak pakai database)
    private static final String SESSION_USER_LIST = "userList";

    // ========================
    // LOGIN
    // ========================

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // Jika sudah login, langsung ke home
        if (session.getAttribute("loggedIn") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // Username: admin, Password: NIM kamu (ganti sesuai NIM)
        String nimPassword = "20210140019";

        if ("admin".equals(username) && nimPassword.equals(password)) {
            session.setAttribute("loggedIn", true);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau password salah!");
            return "login";
        }
    }

    // ========================
    // HOME
    // ========================

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        List<User> userList = getUserList(session);
        model.addAttribute("userList", userList);
        return "home";
    }

    // ========================
    // FORM INPUT
    // ========================

    @GetMapping("/form")
    public String formPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/form")
    public String submitForm(
            @ModelAttribute User user,
            HttpSession session) {

        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        List<User> userList = getUserList(session);
        userList.add(user);
        session.setAttribute(SESSION_USER_LIST, userList);

        return "redirect:/home";
    }

    // ========================
    // LOGOUT
    // ========================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ========================
    // HELPER
    // ========================

    @SuppressWarnings("unchecked")
    private List<User> getUserList(HttpSession session) {
        List<User> userList = (List<User>) session.getAttribute(SESSION_USER_LIST);
        if (userList == null) {
            userList = new ArrayList<>();
            session.setAttribute(SESSION_USER_LIST, userList);
        }
        return userList;
    }
}