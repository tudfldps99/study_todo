package com.example.study_todo.security;

import com.example.study_todo.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

// 토큰을 발급하고, 서명위조를 확인해주는 객체
@Service
@Slf4j
public class TokenProvider {

    // 토큰 서명에 사용할 불변성을 가진 비밀키 (512바이트 이상의 랜덤문자열)
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJbal13eqgB0F7Bq4H";

    // 토큰 발급 메서드
    public String createToken(UserEntity userEntity) {      // 로그인 한 회원 정보 받아옴
        // 토큰 만료시간 설정
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));        // 하루

        // 토큰 생성
        return Jwts.builder()
                // token header 에 들어갈 서명
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.HS512
                )
                .setSubject(userEntity.getId())     // sub : 토큰 식별자 - 유일한 값을 넣음 = 주로 로그인 한 회원정보를 넣음
                .setIssuer("todo app")              // iss : 발급자 정보 - 주로 서비스 이름
                .setIssuedAt(new Date())            // iat : 토큰 발급 시간 - 주로 현재 시간
                .setExpiration(expiryDate)          // exp : 토큰 만료 시간
                .compact();                         // build 역할
    }

    /*
     * 클라이언트가 보낸 토큰을 디코딩 및 파싱해서 토큰위조 여부 확인
     * @param token - 클라이언트가 전송한 인코딩된 토큰
     * @return - 토큰에서 subject(userId)를 꺼내서 반환
     */
    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()        // 토큰을 파싱해주는 builder
                // 토큰 발급자의 발급 당시 서명을 넣어줌
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                // parseClaimsJws : 토큰을 디코딩 서명기록 파싱
                // 클라이언트 토큰의 서명과 서버발급 당시 서명을 비교
                // 위조되지 않았다면 body 에 페이로드(Claims, 데이터 덩어리)를 return
                // 위조되었으면 예외를 발생시킴
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}