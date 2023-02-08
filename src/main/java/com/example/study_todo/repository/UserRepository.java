package com.example.study_todo.repository;

import com.example.study_todo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 검색
    // select * from tbl_user where email=?
    UserEntity findByEmail(String email);   // -> UserEntity.java 의 email 이 유일해야 함 (unique = true)

    // 이메일 중복 검사 (회원가입 시 사용)
    // select count(*) from tbl_user where email=?      -> true(1건) : 중복O, false(0건) : 중복X
    // @Query("select count(*) from UserEntity u where u.email=?1")     <-- JPQL 사용
    boolean existsByEmail(String email);
}
