package com.example.study_todo.exception;

// 사용자 정의 예외 클래스 : 이메일 중복 에러

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedEmailException extends RuntimeException{

    // 기본 생성자 -> lombok) NoArgsConstructor

    // 에러 메시지를 처리하는 생성자
    public DuplicatedEmailException(String message) {
        super(message);
    }
}
