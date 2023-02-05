package com.example.study_todo.repository;

import com.example.study_todo.entity.TodoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoRepositoryTest {

    @Autowired      // 의존관계 주입(DI)을 할 때 사용. 의존 객체의 타입에 해당하는 bean 을 찾아 주입하는 역할
    TodoRepository todoRepository;

    @BeforeEach
    void insertTest() {
        TodoEntity todo1 = TodoEntity
                .builder()
                .title("공부하기")
                .build();
        TodoEntity todo2 = TodoEntity
                .builder()
                .title("공부하기")
                .build();
        TodoEntity todo3 = TodoEntity
                .builder()
                .title("공부하기")
                .build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
    }

    @Test
    @DisplayName("할 일 목록을 조회하면 리스트의 사이즈가 3이어야 한다.")
    void findAllTest() {
        // given

        // when
        List<TodoEntity> list = todoRepository.findAll();

        // then
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("완료되지 않은 할일 목록을 조회하면 리스트의 사이즈가 3이어야 한다.")
    void findNotYetTodos() {
        // given

        // when
        List<TodoEntity> list = todoRepository.findNotYetTodos();

        // then
        assertEquals(3, list.size());
    }
}