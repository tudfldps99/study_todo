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
