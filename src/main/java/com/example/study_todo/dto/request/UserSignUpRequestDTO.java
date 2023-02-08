package com.example.study_todo.dto.request;

import com.example.study_todo.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// 회원가입 시 클라이언트가 보낸 데이터를 담는 객체 (사용자가 입력하는 값들)
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@Builder
public class UserSignUpRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(min = 2, max = 5)
    private String userName;

    // 클라이언트가 보낸 데이터를 엔터티로 변경하는 메서드
    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(this.email)
                .password(this.password)
                .userName(this.userName)
                .build();
    }
}
