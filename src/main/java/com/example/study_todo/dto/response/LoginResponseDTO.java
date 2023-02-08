package com.example.study_todo.dto.response;

import com.example.study_todo.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoginResponseDTO {     // 로그인 후 클라이언트에게 내려줄 정보

    private String email;

    private String userName;

    @JsonFormat(pattern = "yyyy년 MM일 dd일")
    private LocalDate joinDate;

    private String token;       // 인증 토큰

    private String message;     // 응답 메시지

    // 엔터티를 DTO 로 변경
    public LoginResponseDTO(UserEntity userEntity, String token) {
        this.email = userEntity.getEmail();
        this.userName = userEntity.getUserName();
        this.joinDate = LocalDate.from(userEntity.getJoinDate());
        this.token = token;
    }
}
