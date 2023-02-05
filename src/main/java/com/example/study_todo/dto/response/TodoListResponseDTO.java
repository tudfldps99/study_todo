package com.example.study_todo.dto.response;

import lombok.*;

import java.util.List;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoListResponseDTO {      // 할 일 목록 DTO
    private String error;       // 에러 발생 시 클라이언트에게 전달할 메시지
    private List<TodoDetailResponseDTO> todos;
}
