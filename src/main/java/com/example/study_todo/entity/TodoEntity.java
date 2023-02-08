package com.example.study_todo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter         // 접근자/설정자 자동 생성
@ToString               // toString() 메소드 자동 생성
@NoArgsConstructor      // 생성자 자동 생성 : 파라미터가 없는 기본 생성자
@AllArgsConstructor     // 생성자 자동 생성 : 모든 필드 값을 파라미터로 받는 생성자
@EqualsAndHashCode(of = "todoId")       // equals, hashCode 메소드 자동 생성
                                        // - equals : 두 객체의 내용이 같은지, 동등성을 비교하는 연산자
                                        // - hashCode : 두 객체가 같은 객체인지, 동일성을 비교하는 연산자
@Builder                // 빌드 패턴 자동 생성

@Entity                 // 데이터베이스 테이블
@Table(name = "tbl_todo")       // 테이블 명칭 지정
public class TodoEntity {           // DB 와 연결할 때만

    @Id                 // 테이블 상의 Primary Key
    @GeneratedValue(generator = "system-uuid")      // 랜덤으로 생성
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String todoId;

    @Column(nullable = false, length = 30)
    public String title;        // 일정 제목

    private boolean done;       // 일정 완료 여부

    @CreationTimestamp
    private LocalDateTime createDate;       // 일정 등록 시간

    // 연관관계 매핑) 회원 Entity 와 관계 설정
    // 할 일이 다, 회원이 일
    @ManyToOne(fetch = FetchType.LAZY)      // (할일이 다, 회원이 일)
    // 연관관계 설정은 했지만, INSERT, UPDATE 시에는 이 객체를 활용하지 않겠다.
    // SELECT 시에만 사용하겠다.
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    // 할 일 추가(INSERT), 수정(UPDATE) 시 사용할 외래키
    @Column(name = "user_id")
    private String userId;
}
