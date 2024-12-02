package com.moim.moimapiserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moim.moimapiserver.member.IMemberMapper;
import com.moim.moimapiserver.member.MemberDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final IMemberMapper iMemberMapper;
    private final ObjectMapper objectMapper;

    public CustomAuthenticationSuccessHandler(IMemberMapper iMemberMapper, ObjectMapper objectMapper) {
        this.iMemberMapper = iMemberMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();

        if (principal instanceof MemberDto) {
            MemberDto memberDto = (MemberDto) principal;

            log.info("[security] login success - Member Info: {}", memberDto);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(memberDto));
        } else {
            log.error("Invalid principal type: {}", principal.getClass().getName());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid user authentication");
        }
    }
}