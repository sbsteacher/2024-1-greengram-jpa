package com.green.greengram.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

//잘못된 토큰, 만료된 토큰, 지원하지 않는 토큰 처리
/*
    인증이 되어야만 사용할 수 있는 엔드포인트인데 인증이 안 된 경우 아래 commence 메소드가 호출이 된다.
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401에러 리턴
    }
}
