package ru.tinkoff.edu.java.scrapper.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.User;

import java.util.List;

@Repository("userRepository")
public class JdbcUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int add(long id)
    {
        return jdbcTemplate.update("INSERT INTO users (Id) VALUES (?)",id);
    }

    public int remove(long id)
    {
        return jdbcTemplate.update("DELETE FROM users WHERE Id = ?",id);
    }

    public List<User> findAll()
    {
        return jdbcTemplate.query("SELECT * FROM users",new UserMapper());
    }
}
