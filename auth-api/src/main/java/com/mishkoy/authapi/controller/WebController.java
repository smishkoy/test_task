package com.mishkoy.authapi.controller;

import com.mishkoy.authapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // Важно: просто Controller, не RestController!
@RequiredArgsConstructor
public class WebController {

    private final AuthService authService;

    // 1. Главная страница "/"
    @GetMapping("/")
    public String index() {
        return "web/index"; // Ищет файл templates/index.html
    }

    // 2. Страница логина (Заглушка)
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String message, Model model) {
        if (message != null) {
            model.addAttribute("infoMessage", message);
        }
        return "web/login"; // Ищет templates/login.html
    }

    // 3. Страница регистрации (Показать форму)
    @GetMapping("/register")
    public String registerPage() {
        return "web/register"; // Ищет templates/register.html
    }

    // 4. Обработка формы регистрации
    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        try {
            authService.register(email, password);
            // Успех -> идем на логин
            return "redirect:/login?message=Registration successful! Please login.";
        } catch (RuntimeException e) {
            // Если ошибка "Email already exists" (как мы писали в AuthService)
            if (e.getMessage().equals("Email already exists")) {
                // Переходим на логин с сообщением, как ты просил
                return "redirect:/login?message=Email already exists. Please login.";
            }
            // Другая ошибка -> остаемся на странице регистрации и показываем ошибку
            model.addAttribute("error", e.getMessage());
            return "web/register";
        }
    }
}