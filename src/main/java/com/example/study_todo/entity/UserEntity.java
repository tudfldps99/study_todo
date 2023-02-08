package com.example.study_todo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")       // id 만 비교하라는 의미
@Builder
@Entity
@Table(name = "tbl_user")
public class UserEntity {       // 데이터베이스 접근 시 사용하는 객체

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "system-uuid")      // 랜덤으로 생성
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;      // 계정명이 아니라 식별코드

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @CreationTimestamp
    private LocalDateTime joinDate;     // 회원가입 날짜
}
