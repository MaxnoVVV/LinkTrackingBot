package ru.tinkoff.edu.java.scrapper.web.services.jpa;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.web.entity.User;
import ru.tinkoff.edu.java.scrapper.web.repository.jpa.JpaUserRepository;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;

import java.util.Arrays;

@Slf4j
public class JpaTgChatService implements TgChatService {

    private JpaUserRepository repository;

    public JpaTgChatService(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> register(long tgChatId) {
        log.info("registering " + tgChatId);
        User user = new User();
        user.setTgId(tgChatId);
        try {
            User responseUser = repository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiErrorResponse("no links added",
                    "400",
                    "no links added",
                    "no links added",
                    null), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    @Transactional
    public ResponseEntity<?> unregister(long tgChatId) {
        User user = new User();
        user.setTgId(tgChatId);
        log.info("unregistering " + tgChatId);
        if (repository.findAllByTgId(tgChatId).isEmpty()) {
            return new ResponseEntity<>(new ApiErrorResponse("chat not found",
                    "400",
                    "chat not found",
                    "chat not found",
                    null), HttpStatus.BAD_REQUEST);
        }
        try {
            repository.deleteAllByTgId(tgChatId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(new ApiErrorResponse(e.getMessage(),
                    "400",
                    e.toString(),
                    e.getMessage(),
                    Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)), HttpStatus.BAD_REQUEST);
        }
    }
}
