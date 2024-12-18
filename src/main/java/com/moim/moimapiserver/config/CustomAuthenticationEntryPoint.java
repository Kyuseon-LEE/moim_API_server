package com.moim.moimapiserver.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("인증실패: {}", authException.getMessage()); // 로그 출력

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(
                "<script>" +
                        "alert('로그인이 필요한 서비스입니다. 로그인 페이지로 이동합니다.');" +
                        "location.href='/';" +  // 로그인 페이지로 이동
                        "</script>"
        );
    }
}
