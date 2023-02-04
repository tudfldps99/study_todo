package com.example.study_todo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     // JSON 으로 데이터를 주고받음을 선언
@Slf4j              // 로깅 추상화 라이브러리
public class HealthCheckController {        // Health Check : 서버 상태 확인

    @GetMapping
    public ResponseEntity<?> check() {
        log.info("server is running...");

        return ResponseEntity
                .ok()
                .body("server is running...");
    }
}
