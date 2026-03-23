package com.example.project1.security;

import com.example.project1.exception.ErrorCode;
import com.example.project1.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor //스프링이 생성자 주입을 받기 위해 사용
public class ExceptionFilter extends OncePerRequestFilter {//토큰 검증 중 예외가 발생한 경우

    private final ObjectMapper objectMapper;//의존성 주입을 통해 ObjectMapper를 사용하여 JSON 응답을 작성할 수 있도록 합니다.

    @Override protected void doFilterInternal(HttpServletRequest request,
                                              HttpServletResponse response,
                                              FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Unhandled exception in filter chain", ex);
            writeError(response);
        }
    }

    private void writeError(HttpServletResponse response) throws IOException {
        response.setStatus(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8"); // UTF-8 설정 추가
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ExceptionResponse body = ExceptionResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        response.getWriter().write(objectMapper.writeValueAsString(body));

    }
}
