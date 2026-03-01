package com.example.project1.security;

import com.example.project1.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security의 UserDetails 인터페이스 구현체
 * 엔티티(User)를 담아 보안 필터 체인에 사용자의 정보를 전달하는 어댑터 역할을 함
 */
public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 엔티티의 role 필드(String)를 가져와 ROLE_ 접두사를 붙여 권한 부여
        // SimpleGrantedAuthority는 문자열 권한을 시큐리티가 이해하는 객체로 바꿔줌
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        // 보안 필터가 입력받은 비번과 DB 비번을 비교할 수 있도록 실제 비밀번호 반환
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // 유저를 식별할 고유값 반환 (이메일 로그인 방식이므로 email 반환)
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부: 만료 안 됨
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부: 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부: 만료 안 됨
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부: 활성화됨
    }
}