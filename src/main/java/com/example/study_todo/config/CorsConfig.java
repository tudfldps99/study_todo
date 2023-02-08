package com.example.study_todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // api cors 정책 설정
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")        // api 요청 허용 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // api 요청 허용 메소드
                .allowedHeaders("*")
                .allowCredentials(true)     // 자격증명
                .maxAge(3600);      // (초 단위) - 1시간
    }
}
