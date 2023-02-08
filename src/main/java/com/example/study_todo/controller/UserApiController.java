package com.example.study_todo.controller;

import com.example.study_todo.dto.request.UserSignUpRequestDTO;
import com.example.study_todo.dto.response.UserSignUpResponseDTO;
import com.example.study_todo.exception.DuplicatedEmailException;
import com.example.study_todo.exception.NoRegisteredArgumentsException;
import com.example.study_todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")        // 공통 URL
public class UserApiController {

    private final UserService userService;

    // 회원가입 요청 처리
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Validated @RequestBody UserSignUpRequestDTO signUpRequestDTO,
            BindingResult result
    ) {
        // 검증에러 (패스워드 미작성 or 패스워드 8자리 이상 등)
        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        log.info("/api/auth/signup POST - {}", signUpRequestDTO);

        // 예외 상황 2가지 (1. dto 가 null 인 문제, 2. 이메일 중복 문제) - UserService.java 의 create 메소드 확인 (throw 2개)
        // --> NoRegisteredArgumentsException.java, DuplicatedEmailException.java 생성
        try {
            UserSignUpResponseDTO responseDTO = userService.create(signUpRequestDTO);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (NoRegisteredArgumentsException e) {
            log.warn("필수 가입 정보를 다시 확인하세요.");
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        } catch (DuplicatedEmailException e) {
            log.warn("이메일이 중복되었습니다. 다른 이메일을 작성해주세요.");
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
