package com.example.study_todo.controller;

import com.example.study_todo.dto.request.TodoCreateRequestDTO;
import com.example.study_todo.dto.request.TodoModifyRequestDTO;
import com.example.study_todo.dto.response.TodoListResponseDTO;
import com.example.study_todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController     // controller + @ResponseBody --> JSON/XML 형태로 객체 데이터 반환 목적
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")     // crossOrigin 문제 해결
public class TodoApiController {

    private final TodoService todoService;

    // 할 일 목록 요청 (GET)
    @GetMapping
    public ResponseEntity<?> listTodo() {
        log.info("/api/todos GET request");

        TodoListResponseDTO responseDTO = todoService.retrieve();

        return ResponseEntity
                .ok()
                .body(responseDTO);
    }

    // 할 일 등록 요청 (POST)
    @PostMapping
    public ResponseEntity<?> createTodo(@Validated @RequestBody TodoCreateRequestDTO requestDTO, BindingResult result) {        // Validated : 데이터 유효성 검증
                                                                                                                                // RequestBody : HttpRequest 의 본문 RequestBody 의 내용을 자바 객체로 매핑
                                                                                                                                // BindingResult : ModelAttribute 을 이용하여 매개변수를 Bean 에 binding 할 때, 발생한 오류 정보를 받기 위해 선언해야 하는 어노테이션
        log.info("/api/todos POST request");

        if (result.hasErrors()) {       // 검증 에러가 발생하면 badRequest
            log.warn("DTO 검증 에러 발생: {}", result.getFieldError());

            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
            TodoListResponseDTO responseDTO = todoService.create(requestDTO);

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            log.error(e.getMessage());

            return ResponseEntity
                    .internalServerError()          // Internal Server Error 가 나오는 경우는 에러 코드로 500번대 에러 메세지가 나오는 경우 (서버의 문제)
                    .body(TodoListResponseDTO
                            .builder()
                            .error(e.getMessage()));        // TodoListResponseDTO.java 의 error 에 담김
        }
    }

    // 할 일 수정 요청 (PUT, PATCH)
    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> updateTodo(
                @PathVariable("id") String todoId,
                @Validated @RequestBody TodoModifyRequestDTO requestDTO,
                BindingResult result,
                HttpServletRequest request      // PUT 인지, PATCH 인지 요청정보를 알 수있음
    ) {
        if (result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/todos/{} {} request", todoId, request.getMethod());
        log.info("modifying dto: {}", requestDTO);

        try {
            TodoListResponseDTO responseDTO = todoService.modify(todoId, requestDTO);

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO
                            .builder()
                            .error(e.getMessage()));
        }
    }

    // 할 일 삭제 요청 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String todoId) {    // PathVariable : id를 경로에서 읽음

        log.info("/api/todos/{} DELETE request", todoId);

        if (todoId == null || todoId.equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(TodoListResponseDTO
                            .builder()
                            .error("ID를 전달해주세요"));      // TodoListResponseDTO.java 의 error 에 담김
        }

        try {
            TodoListResponseDTO responseDTO = todoService.delete(todoId);

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch(Exception e) {
            log.error(e.getMessage());

            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO
                            .builder()
                            .error(e.getMessage()));        // e.getMessage() 는 TodoService.java 에서 delete 메소드의 throw new RuntimeException() 을 의미
        }

    }

}
