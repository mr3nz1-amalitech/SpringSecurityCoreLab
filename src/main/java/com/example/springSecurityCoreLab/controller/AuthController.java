package com.example.springSecurityCoreLab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    @ResponseBody
    public String register() {
        return "<h1>Login</h1>";
    }
}
