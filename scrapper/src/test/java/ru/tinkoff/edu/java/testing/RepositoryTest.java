package ru.tinkoff.edu.java.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.User;
import ru.tinkoff.edu.java.scrapper.web.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.web.repository.JdbcUserRepository;
import ru.tinkoff.edu.java.testing.configuration.IntegrationTestsConfiguration;

import javax.sql.DataSource;
import java.io.IOException;


@SpringBootTest(classes = {ScrapperApplication.class})
@Import(IntegrationTestsConfiguration.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {IntegrationTestsConfiguration.class})
public class RepositoryTest extends IntegrationEnvironment{

    @Autowired
    JdbcLinkRepository linkRepository;
    @Autowired
    JdbcUserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    Environment env;

    @Test
    public void  simpletest() throws IOException {

        userRepository.add(1);
        userRepository.add(2);
        userRepository.add(3);

        linkRepository.add(3,"stackoverflow.com");
        linkRepository.add(2,"github.com");
        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM users",new DataClassRowMapper<>(User.class)).size() == 3);

        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM users",new DataClassRowMapper<>(User.class)).stream().anyMatch(u -> u.id() == 1));
        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM users",new DataClassRowMapper<>(User.class)).stream().anyMatch(u -> u.id() == 2));
        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM users",new DataClassRowMapper<>(User.class)).stream().anyMatch(u -> u.id() == 3));


        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM links",new DataClassRowMapper<>(Link.class)).size() == 2);
        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM links",new DataClassRowMapper<>(Link.class)).stream().anyMatch(u -> u.getLink().equals("github.com")));
        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM links",new DataClassRowMapper<>(Link.class)).stream().anyMatch(u -> u.getLink().equals("stackoverflow.com")));

        System.out.println(jdbcTemplate.query("SELECT * FROM links",new DataClassRowMapper<>(Link.class)));
        Assert.assertTrue(jdbcTemplate.query("SELECT * FROM links",new DataClassRowMapper<>(Link.class)).size() == 1);
    }


}
