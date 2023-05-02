package ru.tinkoff.edu.java.bot.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.web.dto.ApiErrorResponse;

@RestControllerAdvice
public class BotControllerAdvice  {
    @ExceptionHandler({NullPointerException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(Exception ex) {
        return new ResponseEntity<>(new ApiErrorResponse("error", "400", "MethodArgumentNotValidException", "error", new String[]{"error"}), HttpStatus.BAD_REQUEST);
    }
}
