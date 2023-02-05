# 순서

복습 날짜 : 2023-02-04

## 기본 설정 + 데이터베이스 연결
1. https://start.spring.io/ 에서 프로젝트 다운로드

   (Dependencies : Spring Web, Lombok, Spring Boot Dev Tools, Thymeleaf, Spring Data JPA, MariaDB Driver, Validation)
2. GitHub 연동 후 Help.md -> README.md 파일명 변경
3. .gitignore 에서 README.md 써있는 줄 지우기
4. StudyTodoApplication.java (메인) 톰캣 실행 -> 데이터베이스 연결 안해서 오류 발생
5. application.properties 데이터베이스 연결 (MySQL Workbench 에서 스키마 존재 해야 함 - studydb)
![img.png](README_IMG/application.properties.png)
![img.png](README_IMG/MySQL_Schemas.png)
6. 톰캣 재실행 -> 오류 X
7. 설정 -> 빌드,실행,배포 -> 컴파일러 : 프로젝트 자동 빌드 [체크]
8. 설정 -> 고급설정 -> 개발된 애플리케이션이 현재 실행중인 경우에도 auto-make 가 시작되도록 허용 [체크]

## 서버 연결 확인 설정
1. src/main/java/com/example/study_todo/HealthCheckController.java 생성 및 작성
2. 톰캣 실행 시 server is running... 뜨는지 확인 (http://localhost:8080)

## Entity, Repository, Service, Controller 작성
![img.png](README_IMG/Spring.png)

- Entity 
: Domain

1. src/main/java/com/example/study_todo/entity/TodoEntity.java 생성 및 작성


- Repository
: Entity 에 의해 생성된 DB 에 접근하는 메서드들을 사용하기 위한 인터페이스

2. src/main/java/com/example/study_todo/repository/TodoRepository.java (interface) 생성 및 작성
3. Test 파일 생성 = repository 에서 Ctrl + Shift + t 로 테스트파일 생성 (TodoRepositoryTest.java)
![img.png](README_IMG/create_TestFile.png)

- Service
: 비즈니스 로직을 수행하고 DB에 접근하는 DAO 를 이용해서 결과값을 받아옴 (중간처리) (DAO - Repository(JPA))

4. src/main/java/com/example/study_todo/service/TodoService.java 생성 및 작성

- DTO
: 

-> TodoService.java 수정하면서 DTO 파일 생성

5. src/main/java/com/example/study_todo/dto/response/TodoListResponseDTO.java 생성 및 작성
6. src/main/java/com/example/study_todo/dto/response/TodoDetailResponseDTO.java 생성 및 작성
7. src/main/java/com/example/study_todo/dto/request/TodoCreateRequestDTO.java 생성 및 작성
8. src/main/java/com/example/study_todo/dto/request/TodoModifyRequestDTO.java 생성 및 작성
9. Test 파일 생성 = repository 에서 Ctrl + Shift + t 로 테스트파일 생성 (TodoServiceTest.java)
