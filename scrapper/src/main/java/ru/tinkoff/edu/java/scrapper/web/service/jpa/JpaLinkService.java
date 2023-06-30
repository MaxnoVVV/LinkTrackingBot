package ru.tinkoff.edu.java.scrapper.web.service.jpa;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.ParseResult;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.LinkResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.web.entity.Link;
import ru.tinkoff.edu.java.scrapper.web.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.web.service.LinkService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JpaLinkService implements LinkService {
    private Parser parser;
    private JpaLinkRepository repository;

    public JpaLinkService(JpaLinkRepository repository, Parser parser) {
        this.repository = repository;
        this.parser = parser;
    }


    @Override
    public int update(long tgChatId,String link) {
        List<Link> links = repository.findByLinkAndTracking_user(link,tgChatId);
        if(links.isEmpty()) return 0;
        Link link_ = links.get(0);
        link_.setLast_check(OffsetDateTime.now(ZoneOffset.UTC));
        repository.save(link_);
        return 1;
    }

    @Override
    public ResponseEntity<?> add(long tgChatId, URI uri) {

        Link link = new Link();
        link.setLink(uri.toString());
        link.setLast_check(OffsetDateTime.now(ZoneOffset.UTC));
        link.setTrackinguser(tgChatId);

        ParseResult parseResult = parser.parse(uri.toString());

        if (parseResult != null && repository.findByLinkAndTracking_user(uri.toString(), tgChatId).isEmpty()) {
            Link newLink = repository.save(link);
            return new ResponseEntity<>(new LinkResponse(newLink.getLink_id(), uri.toString()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiErrorResponse("no links added",
                    "400",
                    "no links added",
                    "no links added",
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> remove(long tgChatId, URI uri) {
        Link link = new Link();
        link.setLink(uri.toString());
        link.setLast_check(OffsetDateTime.now(ZoneOffset.UTC));
        link.setTrackinguser(tgChatId);

        List<Link> linksInTable = repository.findByLinkAndTracking_user(uri.toString(), tgChatId);

        if (linksInTable.isEmpty()) {
            return new ResponseEntity<>(new ApiErrorResponse("no links",
                    "400",
                    "no links",
                    "no links",
                    null), HttpStatus.BAD_REQUEST);
        } else {
            try {
                repository.deleteAllByLinkAndTrackinguser(uri.toString(), tgChatId);
                return new ResponseEntity<>(new LinkResponse(linksInTable.get(0).getLink_id(), uri.toString()), HttpStatus.OK);
            } catch (Exception e) {
                log.error(e.toString());
                return new ResponseEntity<>(new ApiErrorResponse(e.getMessage(), "500", e.toString(), e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)), HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
    }

    @Override
    public ResponseEntity<?> listAll(long tgChatId) {
        try {
            List<Link> links = repository.findAllByTracking_user(tgChatId);
            ListLinksResponse response = new ListLinksResponse(links.size(), links.stream().map(u -> new LinkResponse(u.getLink_id(), u.getLink())).toArray(LinkResponse[]::new));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(e.getMessage(), "400", e.toString(), e.getMessage(), Arrays.stream(e.getStackTrace()).map(u -> u.toString()).toArray(String[]::new)), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<ru.tinkoff.edu.java.scrapper.web.dto.repository.Link> findAll()
    {
        return repository.findAll().stream().map(element -> new ru.tinkoff.edu.java.scrapper.web.dto.repository.Link(element.getLink_id(),element.getLink(),element.getTrackinguser(),element.getLast_check())).collect(Collectors.toList());
    }
}
