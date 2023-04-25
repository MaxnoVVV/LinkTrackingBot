package ru.tinkoff.edu.java.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.User;
import ru.tinkoff.edu.java.scrapper.web.services.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.web.services.jdbc.JdbcTgChatService;
import ru.tinkoff.edu.java.testing.configuration.IntegrationTestsConfiguration;

import java.net.URI;

@SpringBootTest(classes = {ScrapperApplication.class})
@Import(IntegrationTestsConfiguration.class)
@RunWith(SpringRunner.class)
public class ServiceTest extends IntegrationEnvironment{

    @Autowired
    JdbcTgChatService jdbcTgChatService;

    @Autowired
    JdbcLinkService jdbcLinkService;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    public void Tests()
        {
            String gitHubLink = "https://github.com/MaxnoVVV/TinkoffBot/";
            String stackOverFlowLink = "https://stackoverflow.com/questions/54444726/spring-boot-test-doesnt-find-bean-from-sibling-package";
            String wrongLink = "https://mail.google.com/mail/u/0/#inbox";

            ResponseEntity response = jdbcTgChatService.register(1);

            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            Assert.assertTrue(jdbcTemplate.query("SELECT * FROM users",new DataClassRowMapper<>(User.class)).stream().anyMatch(u -> u.id() == 1));

            response = jdbcTgChatService.unregister(1);
            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            Assert.assertFalse(jdbcTemplate.query("SELECT * FROM users",new DataClassRowMapper<>(User.class)).stream().anyMatch(u -> u.id() == 1));



            response = jdbcTgChatService.unregister(1);

            Assertions.assertTrue(response.getStatusCode().is4xxClientError());

            response = jdbcTgChatService.register(1);

            response = jdbcLinkService.add(1, URI.create(gitHubLink));
            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

            response = jdbcLinkService.add(1,URI.create(stackOverFlowLink));
            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

            response = jdbcLinkService.add(1,URI.create(wrongLink));
            Assert.assertTrue(response.getStatusCode().is4xxClientError());

            response = jdbcLinkService.listAll(1);

            System.out.println(response.getBody());

            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            Assertions.assertTrue(((ListLinksResponse)response.getBody()).links().length == 2);

            response = jdbcLinkService.remove(1,URI.create(stackOverFlowLink));

            response = jdbcLinkService.listAll(1);
            Assertions.assertTrue(((ListLinksResponse)response.getBody()).links().length == 1);
        }


}
