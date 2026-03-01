package com.example.project1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User { // 1. implements UserDetails 제거 (순수 엔티티화)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    // 2. 권한 필드 추가 (CustomUserDetails에서 이 값을 사용하게 됩니다)
    // 보통 "USER", "ADMIN" 등의 문자열이 저장됩니다.
    @Column(nullable = false)
    private String role;

    @CreationTimestamp
    private LocalDateTime createdAt;
}