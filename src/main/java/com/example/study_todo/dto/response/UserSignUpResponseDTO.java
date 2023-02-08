package com.example.study_todo.dto.response;

import com.example.study_todo.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// 회원가입 완료 후 클라이언트에게 응답할 데이터를 담는 객체
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@Builder
public class UserSignUpResponseDTO {

    private String email;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    // 엔터티를 dto 로 변경하는 생성자
    public UserSignUpResponseDTO(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.userName = userEntity.getUserName();
        this.joinDate = userEntity.getJoinDate();
    }
}
