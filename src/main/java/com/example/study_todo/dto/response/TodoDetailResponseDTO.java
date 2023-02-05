package com.example.study_todo.dto.response;

import com.example.study_todo.entity.TodoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class TodoDetailResponseDTO {        // 할 일 1개 DTO
    // TodoEntity 와 필드가 동일해도, Entity 에서는 DB 와 직접 연결하므로 다른 역할

    private String id;
    private String title;       // 할 일 제목
    private boolean done;       // 할 일 완료 여부
    @JsonFormat(pattern = "yyyy년 MM월 dd일 a hh시 mm분 ss초")        // a : 오전&오후 표기
    private LocalDateTime regDate;

    // entity 를 받아서 DTO 로 만들어주는 생성자 (entity -> DTO)
    public TodoDetailResponseDTO(TodoEntity entity) {
        this.id = entity.getTodoId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
        this.regDate = entity.getCreateDate();
    }
}
