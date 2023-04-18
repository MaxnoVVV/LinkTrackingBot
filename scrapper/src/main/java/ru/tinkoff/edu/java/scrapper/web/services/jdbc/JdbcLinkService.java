package ru.tinkoff.edu.java.scrapper.web.services.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.LinkResponse;
import ru.tinkoff.edu.java.scrapper.web.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.web.services.LinkService;

import java.net.URI;
import java.util.Arrays;

@Component
public class JdbcLinkService  implements LinkService {
    @Autowired
    LinkRepository repository;


    @Override
    public int update() {
        return 0;
    }

    @Override
    public ResponseEntity<?> add(long tgChatId, URI uri) {
        try {

            repository.add(tgChatId, uri.toString());
            var res = new LinkResponse(repository.findAll().stream().filter(u -> u.getLink().equals(uri.toString())).findFirst().get().link_id(), uri.toString());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch(DataAccessException e)
        {
            return new ResponseEntity<>(new ApiErrorResponse("error",
                    "400",
                    e.toString(),
                    e.getMessage(),
                    Arrays.stream(e.getStackTrace()).map(s -> s.toString()).toArray(String[]::new)),HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    public ResponseEntity<?> remove(long tgChatId, URI uri) {
        try
        {
            int link_id = repository.findAll().stream().filter(u -> u.getLink().equals(uri.toString())).findFirst().get().link_id();
            int number = repository.delete(uri.toString());

            if(number == 0)
            {
                return new ResponseEntity<>(new ApiErrorResponse("error","404","No such link","error",null),HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(new LinkResponse(link_id,uri.toString()),HttpStatus.OK);
            }
        }
        catch(DataAccessException e)
        {
            return new ResponseEntity<>(new ApiErrorResponse("error","400",e.toString(),e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> listAll(long tgChatId) {

        try
        {
            return new ResponseEntity<>(repository.findAll().stream().map(u -> new LinkResponse(u.link_id(),u.getLink())).toList(),HttpStatus.OK);
        }
        catch(DataAccessException e)
        {
            return new ResponseEntity<>(new ApiErrorResponse("error","400",e.toString(),e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)),HttpStatus.BAD_REQUEST);
        }
    }
}
