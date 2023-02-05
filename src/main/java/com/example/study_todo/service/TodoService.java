package com.example.study_todo.service;

import com.example.study_todo.entity.TodoEntity;
import com.example.study_todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor        // 초기화 되지 않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해줌
public class TodoService {      // 중간처리

    private final TodoRepository todoRepository;

    // 할 일 목록 조회
    public List<TodoEntity> retrieve() {
        return todoRepository.findAll();
    }
}
