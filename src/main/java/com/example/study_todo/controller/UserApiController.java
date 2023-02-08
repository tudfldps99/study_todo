package com.example.study_todo.controller;

import com.example.study_todo.dto.request.LoginRequestDTO;
import com.example.study_todo.dto.request.UserSignUpRequestDTO;
import com.example.study_todo.dto.response.LoginResponseDTO;
import com.example.study_todo.dto.response.UserSignUpResponseDTO;
import com.example.study_todo.entity.UserEntity;
import com.example.study_todo.exception.DuplicatedEmailException;
import com.example.study_todo.exception.NoRegisteredArgumentsException;
import com.example.study_todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")        // 공통 URL
//@CrossOrigin      //  CrossOrigin 주석 처리 -> /config/CorsConfig.java
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

    // 이메일 중복확인 요청 처리
    // GET : /api/auth/check?email="abc@bbb.com"        <- RequestParam
    @GetMapping("/check")
    public ResponseEntity<?> checkEmail (@RequestParam String email) {      // @RequestParam 생략 가능 (@RequestBody 는 생략 불가능)
        if (email == null || email.trim().equals("")) {     // trim() : 공백 제거
            return ResponseEntity
                    .badRequest()
                    .body("이메일을 전달해주세요.");
        }       // @RequestParam 에 required = true 가 숨어있기때문에 애초에 이메일 값을 안보내면 spring 선에서 안보내기 때문에 이 if 문은 의미 없는 문장
                // 그러나 나중에 변경될 거에 대비하여 작성 (보험코드)

        boolean flag = userService.isDuplicate(email);
        log.info("{} 중복 여부 ? = {}", email, flag);

        return ResponseEntity
                .ok()
                .body(flag);
    }

    // 로그인 요청 처리
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Validated @RequestBody LoginRequestDTO requestDTO) {

        try {
            LoginResponseDTO userInfo = userService.getByCredentials(
                    requestDTO.getEmail(),
                    requestDTO.getPassword()
            );

            return ResponseEntity
                    .ok()
                    .body(userInfo);
        } catch (RuntimeException e) {      // 가입을 안했거나, 비밀번호가 틀렸거나
            return ResponseEntity
                    .badRequest()
                    .body(LoginResponseDTO.builder()
                            .message(e.getMessage())
                            .build()
                    );
        }
    }
}
