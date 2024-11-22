package com.moim.moimapiserver.config;

import com.moim.moimapiserver.member.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        log.info("passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain");

        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers( "/member/**", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("http://localhost:3000/login")
                        .loginProcessingUrl("/member/localLogin")
                        .usernameParameter("m_id")
                        .passwordParameter("m_pw")
                        .defaultSuccessUrl("http://localhost:3000", true) // 로그인 성공 후 리다이렉트
                        .failureUrl("http://localhost:3000") // 로그인 실패 시
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            log.info("[security] login failure: {}", exception.getMessage());
                            log.info("request---->", request.getParameterMap());
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("{\"result\": 2}");
                        })
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 실패 시 처리
                );

        return http.build();
    }
}
