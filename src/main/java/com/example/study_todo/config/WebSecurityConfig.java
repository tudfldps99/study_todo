package com.example.study_todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity      // 사용자 정의 보안 정의 클래스 활성화
public class WebSecurityConfig {        // 사용자 정의 보안 설정 클래스

    // 패스워드 인코딩 클래스 등록 (패스워드 인코딩 클래스 : 내가 등록한 클래스가 아님)
    @Bean
    public PasswordEncoder encoder() {      // BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    // security 설정 (Postman 401 Unauthorized error)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // security builder
        http.cors()         // CORS (크로스오리진) 정책
                .and()
                .csrf()     // CSRF 정책
                .disable()  // 사용 안함 ("너네가 만든 기본 보안정책을 내가 사용하지 않고, 자체적으로 만든것을 사용하겠다")
                .httpBasic().disable()      // 기본 security 인증 해제 (우리는 토큰 인증을 사용할 것이기 때문)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // session 기반 인증 안함 (우리는 토큰 인증을 사용할 것이기 때문)
                .and()
                .authorizeRequests().antMatchers("/", "/api/auth/**").permitAll()       // 인증 요청 중에서 '/' 경로와, '/api/auth' 로 시작하는 경로는 인증하지 않고 모두 허용
                .anyRequest().authenticated();      // 그 외의 모든 경로는 인증을 거쳐야 함

        return http.build();
    }
}
