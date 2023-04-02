package ru.tinkoff.edu.java.bot.web.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.tinkoff.edu.java.bot.web.DTO.ApiErrorResponse;

@RestControllerAdvice
public class BotControllerAdvice  {
    @ExceptionHandler({NullPointerException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(Exception ex) {
        return new ResponseEntity<>(new ApiErrorResponse("error", "400", "MethodArgumentNotValidException", "error", new String[]{"error"}), HttpStatus.BAD_REQUEST);
    }
}
