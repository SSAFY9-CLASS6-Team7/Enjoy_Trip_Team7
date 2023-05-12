package com.ssafy.enjoytrip.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResult> sqlException(SQLException e) {
        log.error("SQL Exception 발생 ! = {} ", e.getMessage());
        ErrorResult errorResult = new ErrorResult("SQL 오류", "SQL 오류가 발생하였습니다.");
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
