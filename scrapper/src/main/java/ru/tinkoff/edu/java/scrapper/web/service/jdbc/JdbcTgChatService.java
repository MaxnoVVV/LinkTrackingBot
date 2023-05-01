package ru.tinkoff.edu.java.scrapper.web.service.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.web.repository.jdbc.JdbcUserRepository;
import ru.tinkoff.edu.java.scrapper.web.service.TgChatService;

import java.util.Arrays;


@Slf4j
public class JdbcTgChatService implements TgChatService {
    private JdbcUserRepository repository;

    public JdbcTgChatService(JdbcUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> register(long tgChatId) {
        try {
            log.info("trying register in service");
            if (repository.findAll().stream().anyMatch(u -> u.id() == tgChatId)) {
                return new ResponseEntity<>(new ApiErrorResponse("Usr exists", "400", "user exists", "user exists", null), HttpStatus.BAD_REQUEST);
            }
            repository.add(tgChatId);
            log.info("success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            log.error("error");
            log.error(e.toString());
            log.error(e.getMessage());

            for (var ex : e.getStackTrace()) {
                log.error(ex.toString());
            }

            return new ResponseEntity<>(new ApiErrorResponse("error", "400", e.toString(), e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> unregister(long tgChatId) {

        try {
            int result = repository.remove(tgChatId);
            if (result != 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse("Chat not found",
                    "400",
                    "Chat not found",
                    "Chat not found",
                    null), HttpStatus.BAD_REQUEST);


        } catch (DataAccessException e) {
            return new ResponseEntity<>(new ApiErrorResponse("error", "400", e.toString(), e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)), HttpStatus.BAD_REQUEST);
        }
    }
}
