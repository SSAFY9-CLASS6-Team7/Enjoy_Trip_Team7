package com.ssafy.enjoytrip.util.controller;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.user.model.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ssafy.enjoytrip.user.model.User;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {

    private final UserServiceImpl userService;
    private static final String SALT = "team7secretKey";

    public String createAccessToken(String userId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(30, ChronoUnit.MINUTES);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return create(userId, "access-token", expirationDate);
    }

    public String createRefreshToken(String userId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(1, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return create(userId, "refresh-token", expirationDate);
    }

    public String create(String userId, String subject, Date date) {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT") // Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
                .setHeaderParam("regDate", System.currentTimeMillis()) // 생성 시간
                .setSubject(subject) // 구분자 설정 : access-token || refresh-token
                .setExpiration(date) // 만료일 설정 (유효기간)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, this.generateKey()) // Signature 설정 : secret key를 활용한 암호화.
                .compact(); // 직렬화
        return jwt;
    }
    // Signature 설정에 들어갈 key 생성.
    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return key;
    }

    public Map<String, Object> get(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthorizedException("회원 ID 획득에 실패하였습니다.");
        }
        Map<String, Object> value = claims.getBody();
        return value;
    }

    public String getUserId(String jwt) {
        return (String) get(jwt).get("userId");
    }

    public User getUserInfo(HttpServletRequest request) throws Exception {
        String accessToken = request.getHeader("access-token");
        if (!checkToken(accessToken)){
            throw new UnAuthorizedException("회원 정보 획득에 실패하였습니다.");
        }

        String userId = getUserId(accessToken);
        User user = userService.getUserInfo(userId);
        if (user == null) {
            throw new UnAuthorizedException("회원 정보 획득에 실패하였습니다.");
        }
        return user;
    }

    public boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            Date expiration = claims.getBody().getExpiration();
            return new Date(System.currentTimeMillis()).before(expiration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnAuthorizedException("Token이 올바르지 않습니다.");
        }
    }
}