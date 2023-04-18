package ru.tinkoff.edu.java.scrapper.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.User;

import java.util.List;

@Repository
public class UserRepository
{
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(long id)
    {
        jdbcTemplate.update("INSERT INTO users (Id) VALUES (?)",id);
    }

    public void remove(long id)
    {
        jdbcTemplate.update("DELETE FROM users WHERE Id = ?",id);
    }

    public List<User> findAll()
    {
        return jdbcTemplate.query("SELECT * FROM users",new UserMapper());
    }
}
