package com.example.project1.controller;

import com.example.project1.dto.SignupRequestDto;
import com.example.project1.entity.User;
import com.example.project1.entity.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class MainController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MainController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String main(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("email", email);
        return "main";
    }




}

