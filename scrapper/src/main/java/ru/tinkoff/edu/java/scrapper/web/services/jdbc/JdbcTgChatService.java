package ru.tinkoff.edu.java.scrapper.web.services.jdbc;

import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.web.repository.UserRepository;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;

import java.util.Arrays;

@Repository
public class JdbcTgChatService implements TgChatService {
    @Autowired
    UserRepository repository;
    @Override
    public ResponseEntity<?> register(long tgChatId) {
        try
        {
            repository.add(tgChatId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(DataAccessException e)
        {
            return new ResponseEntity<>(new ApiErrorResponse("error","400",e.toString(),e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> unregister(long tgChatId) {
        try
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(DataAccessException e)
        {
            return new ResponseEntity<>(new ApiErrorResponse("error","400",e.toString(),e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)),HttpStatus.BAD_REQUEST);
        }
    }
}
