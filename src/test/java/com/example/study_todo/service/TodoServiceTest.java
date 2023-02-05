package com.example.study_todo.service;

import com.example.study_todo.dto.request.TodoCreateRequestDTO;
import com.example.study_todo.dto.request.TodoModifyRequestDTO;
import com.example.study_todo.dto.response.TodoDetailResponseDTO;
import com.example.study_todo.dto.response.TodoListResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Commit
class TodoServiceTest {
    @Autowired
    TodoService todoService;

    @BeforeEach
    void beforeInsert() {
        TodoCreateRequestDTO dto1 = TodoCreateRequestDTO.builder().title("Spring 공부하기").build();
        TodoCreateRequestDTO dto2 = TodoCreateRequestDTO.builder().title("고양이 밥주기").build();
        TodoCreateRequestDTO dto3 = TodoCreateRequestDTO.builder().title("방 청소하기").build();

        todoService.create(dto1);
        todoService.create(dto2);
        todoService.create(dto3);
    }

    @Test
    @DisplayName("새로운 할 일을 등록하면 생성되는 리스트는 할 일이 4개 들어있어야 한다")
    void createTest() {
        // given
        TodoCreateRequestDTO newTodo = TodoCreateRequestDTO.builder()
                .title("새로운 할일")
                .build();

        // when
        TodoListResponseDTO responseDTO = todoService.create(newTodo);

        // then
        List<TodoDetailResponseDTO> todos = responseDTO.getTodos();
        assertEquals(4, todos.size());

        System.out.println("==========================");
        todos.forEach(System.out::println);
    }

    @Test
    @DisplayName("두번째 할일의 제목을 '수정수정' 으로 수정하고, 할일 완료처리(done=true)를 해야한다")
    void updateTest() {
        // given
        String newTitle = "수정수정";
        boolean newDone = true;

        TodoModifyRequestDTO modifyRequestDTO = TodoModifyRequestDTO.builder()
                .title(newTitle)
                .done(newDone)
                .build();

        // when
        TodoDetailResponseDTO targetTodo = todoService.retrieve().getTodos().get(1);// 1번 index : 2번째 data get

        TodoListResponseDTO responseDTO = todoService.modify(targetTodo.getId(), modifyRequestDTO);// update

        // then
        assertEquals("수정수정", responseDTO.getTodos().get(1).getTitle());
        assertTrue(responseDTO.getTodos().get(1).isDone());

        System.out.println("==========================");
        responseDTO.getTodos().forEach(System.out::println);
    }

    @Test
    @DisplayName("3번째 할일을 삭제해야 한다")
    void deleteTest() {
        // given
        int index = 2;

        // when
        String deleteId = todoService.retrieve().getTodos().get(index).getId();// 2번 index : 3번째 data get

        TodoListResponseDTO deleteDTO = todoService.delete(deleteId);

        // then
        System.out.println("==========================");
        deleteDTO.getTodos().forEach(System.out::println);
    }
}