package com.example.study_todo.service;

import com.example.study_todo.dto.request.UserSignUpRequestDTO;
import com.example.study_todo.dto.response.UserSignUpResponseDTO;
import com.example.study_todo.entity.UserEntity;
import com.example.study_todo.exception.DuplicatedEmailException;
import com.example.study_todo.exception.NoRegisteredArgumentsException;
import com.example.study_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;      // final 로 생성하면 @RequiredArgsConstructor 를 이용하여 자동으로 생성자 주입됨

    // 회원가입 처리
    public UserSignUpResponseDTO create (final UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userSignUpRequestDTO == null) {
//            throw new RuntimeException("가입 정보가 없습니다.");       --> NoRegisteredArgumentsException.java 생성
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다.");
        }

        final String email = userSignUpRequestDTO.getEmail();
        if (userRepository.existsByEmail(email)) {      // 이메일 중복검사 - TRUE : 이메일 중복 O
            log.warn("Email already exists - {}", email);
//            throw new RuntimeException("중복된 이메일입니다.");        --> DuplicatedEmailException.java 생성
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }

        // 패스워드 인코딩
        String rawPassword = userSignUpRequestDTO.getPassword();        // 평문 패스워드
        String encodedPassword = passwordEncoder.encode(rawPassword);   // 암호화 처리 패스워드
        userSignUpRequestDTO.setPassword(encodedPassword);              // 암호화된 패스워드 set

        // 회원가입
        UserEntity savedUser = userRepository.save(userSignUpRequestDTO.toEntity());        // DTO 를 entity 로 변환시킴

        log.info("회원 가입 성공 - user_id: {}", savedUser.getId());

        // savedUser 를 그대로 return 하면, 클라이언트에게 비밀번호 등 모든 것을 보여주는 것이므로, UserSignUpResponseDTO 에서 dto 로 변환시켜서 return
        return new UserSignUpResponseDTO(savedUser);
    }

    // 이메일 중복 확인
    public boolean isDuplicate(String email) {
        if (email == null) {
            throw new RuntimeException("이메일 값이 업습니다.");
        }

        return userRepository.existsByEmail(email);
    }
}
