package com.example.study_todo.service;

import com.example.study_todo.dto.request.UserSignUpRequestDTO;
import com.example.study_todo.dto.response.UserSignUpResponseDTO;
import com.example.study_todo.entity.UserEntity;
import com.example.study_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입 처리
    public UserSignUpResponseDTO create (final UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userSignUpRequestDTO == null) {
            throw new RuntimeException("가입 정보가 없습니다.");
        }

        final String email = userSignUpRequestDTO.getEmail();
        if (userRepository.existsByEmail(email)) {      // 이메일 중복검사 - TRUE : 이메일 중복 O
            log.warn("Email already exists - {}", email);
            throw new RuntimeException("중복된 이메일입니다.");
        }

        // 회원가입
        UserEntity savedUser = userRepository.save(userSignUpRequestDTO.toEntity());        // DTO 를 entity 로 변환시킴

        // savedUser 를 그대로 return 하면, 클라이언트에게 비밀번호 등 모든 것을 보여주는 것이므로, UserSignUpResponseDTO 에서 dto 로 변환시켜서 return
        return new UserSignUpResponseDTO(savedUser);
    }
}
