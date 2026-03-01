package com.example.project1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j// JwtFilter 클래스는 JWT 토큰을 검증하는 필터입니다. 이 클래스는 Spring Security의 OncePerRequestFilter를 상속하여 구현됩니다.
@Component// @Component 어노테이션은 Spring이 이 클래스를 Bean으로 등록하도록 지시합니다. 이렇게 하면 Spring Security 설정에서 이 필터를 사용할 수 있습니다.
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 이 클래스의 final 필드에 대한 생성자를 자동으로 생성합니다. 예를 들어, private final JwtUtil jwtUtil; 라는 필드가 있다면, @RequiredArgsConstructor는 public JwtFilter(JwtUtil jwtUtil) { this.jwtUtil = jwtUtil; } 와 같은 생성자를 자동으로 만들어줍니다. 이렇게 하면 의존성 주입이 편리해집니다.
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        //JWT 유효성 검증
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getEmail(token);

            //유저 정보 생성
            UserDetails userDetails= customUserDetailsService.loadUserByUsername(email);

            if (userDetails != null){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer")){
            return token.substring(7);
        }
        return null;
    }
}
