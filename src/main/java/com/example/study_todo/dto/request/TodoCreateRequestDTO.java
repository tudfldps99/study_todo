package com.example.study_todo.dto.request;

import com.example.study_todo.entity.TodoEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
// @Data    // -> 위의 4줄 어노테이션들을 한번에 사용할 수 있지만 유지보수(custom)에 대비해서 사용 지양
@Builder
public class TodoCreateRequestDTO {

    @NotBlank
    @Size(min = 2, max = 10)
    private String title;       // 할 일 등록 시 제목만 입력받음

    // 이 DTO 를 entity 로 변환  (DTO -> entity)
    public TodoEntity toEntity() {
        return TodoEntity
                .builder()
                .title(this.title)
                .build();
    }
}
