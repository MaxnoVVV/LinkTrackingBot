package ru.tinkoff.edu.java.scrapper.web.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;

import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    public LinkRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Link> findAll()
    {
        return jdbcTemplate.query("SELECT * FROM links",new LinkMapper());
    }

    public void add(long userId,String url) // дата?
    {
        jdbcTemplate.update("INSERT INTO links (link,tracking_user,last_check) VALUES (?,?,?)",url,userId, OffsetDateTime.now());
    }

    public int delete(String link)
    {
        return jdbcTemplate.update("DELETE FROM links WHERE link = ",link);
    }


}
