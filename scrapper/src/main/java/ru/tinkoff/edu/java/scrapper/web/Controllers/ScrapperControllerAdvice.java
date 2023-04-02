package ru.tinkoff.edu.java.scrapper.web.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.web.DTO.controllers.ApiErrorResponse;

@RestControllerAdvice
public class ScrapperControllerAdvice {

    @ExceptionHandler({NumberFormatException.class,MissingRequestHeaderException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorResponse> handleNumberFormatException(Exception e)
    {
        return new ResponseEntity<>(new ApiErrorResponse("error", "400", "NumberFormatException", e.getMessage(), new String[]{"error"}),
                HttpStatus.BAD_REQUEST);
    }
}
