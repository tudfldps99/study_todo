package com.example.study_todo.repository;

import com.example.study_todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {     // JPA : 매우 적은 코드로 DAO 구현 가능
                                                                                //       인터페이스를 만드는 것만으로도 Entity 클래스에 대한 insert, update, delete, select 실행 가능
                                                                                // JpaRepository< Entity 클래스 명, Entity 클래스의 PK 자료형>
    // 완료되지 않은 할일들만 조회
    @Query("SELECT t FROM TodoEntity t WHERE t.done = 0")
    List<TodoEntity> findNotYetTodos();
}
