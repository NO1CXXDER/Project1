package com.example.project1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;

    private String name;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email; //로그인 시 아이디(username)으로 email을 사용하도록 설정
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //계정 만료 여부(true : 만료안됨)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//계정 잠금 여부((true: 만료 안 됨))
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //비밀 번호 만료 여부(true: 만료 안 됨)
    }

    @Override
    public boolean isEnabled() {
        return true;//계정 활성화 여부( true: 활성)
    }
}