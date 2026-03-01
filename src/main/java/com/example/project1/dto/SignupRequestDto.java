package com.example.project1.dto;

import com.example.project1.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
public class SignupRequestDto {
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;

    public User toUser(PasswordEncoder passwordEncoder){
        return User.builder() //public UserBuilder builder() {
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();

    }
}
