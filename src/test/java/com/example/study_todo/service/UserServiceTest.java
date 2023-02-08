package com.example.study_todo.service;

import com.example.study_todo.dto.request.UserSignUpRequestDTO;
import com.example.study_todo.dto.response.LoginResponseDTO;
import com.example.study_todo.dto.response.UserSignUpResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @Test
    @DisplayName("중복된 이메일이 포함된 회원정보로 가입하면 RuntimeException 이 발생해야 한다.")
    void validateEmailTest() {      // 의도적인 실패 테스트
        // given
        UserSignUpRequestDTO dto = UserSignUpRequestDTO.builder()
                .email("abc1234@def.com")
                .password("5678")
                .userName("개똥이")
                .build();

        // when
//        userService.create(dto);

        // then
        assertThrows(RuntimeException.class, () -> {
            userService.create(dto);        // --> 에러가 발생할 거라고 단언 했으므로 테스트 성공
        });
    }

    @Test
    @DisplayName("검증된 회원정보로 가입하면 회원가입에 성공해야 한다.")
    void createTest() {
        // given
        UserSignUpRequestDTO dto = UserSignUpRequestDTO.builder()
                .email("asdf9012@def.com")
                .password("9012")
                .userName("소똥이")
                .build();

        // when
        UserSignUpResponseDTO responseDTO = userService.create(dto);

        // then
        System.out.println("responseDTO = " + responseDTO);
        assertEquals("소똥이", responseDTO.getUserName());
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인을 시도하면 Exception이 발생해야 한다.")
    void noUserTest() {
        // given
        String email = "dkdkdk12@sss.com";      // 존재하지 않는 이메일
        String password = "ddkdkdk";

        // then
        assertThrows(RuntimeException.class, () -> {
            // when
            userService.getByCredentials(email, password);
        });
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인을 시도하면 Exception 이 발생해야 한다.")
    void invalidPasswordTest() {
        // given
        String email = "asdf9012@def.com";
        String password = "1111";       // 틀린 비밀번호

        // then
        assertThrows(RuntimeException.class, () -> {
            // when
            userService.getByCredentials(email, password);
        });
    }

    @Test
    @DisplayName("정확한 정보로 로그인을 시도하면 회원정보가 반환되어야 한다.")
    void loginTest() {
        // given
        String email = "asdf9012@def.com";
        String password = "9012";

        // when
        LoginResponseDTO loginUser = userService.getByCredentials(email, password);

        // then
        assertEquals("소똥이", loginUser.getUserName());
        System.out.println(loginUser);

    }
}