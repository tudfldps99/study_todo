package com.example.study_todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity      // 사용자 정의 보안 정의 클래스 활성화
public class WebSecurityConfig {        // 사용자 정의 보안 설정 클래스

    // 패스워드 인코딩 클래스 등록 (패스워드 인코딩 클래스 : 내가 등록한 클래스가 아님)
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
