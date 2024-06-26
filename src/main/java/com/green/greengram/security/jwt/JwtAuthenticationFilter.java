package com.green.greengram.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProviderV2 jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // header의 authorization키에 저장되어 있는 값을 리턴(있으면 문자열(JWT), 없으면 null)
        // JWT값이 있으면 로그인 상태, null이면 비로그인 상태(로그아웃 상태)
        String token = jwtTokenProvider.resolveToken(request);
        log.info("JwtAuthenticationFilter-Token: {}", token);

        //토큰이 정상적으로 저장되어 있고, 만료가 되지 않았다면 >> 로그인 처리
        if(token != null && jwtTokenProvider.isValidateToken(token)) {
            //SecurityContextHolder의 Context의 담기 위한 Authentication 객체 생성
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            if(auth != null) {
                //Authentication 객체 주소값을 담으면 인증되었다고 인식
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
