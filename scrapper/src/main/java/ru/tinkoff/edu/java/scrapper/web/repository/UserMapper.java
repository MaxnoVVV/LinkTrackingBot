package ru.tinkoff.edu.java.scrapper.web.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet set, int i) throws SQLException {
        User user = new User(set.getInt("user_id"), set.getInt("Id"));
        return user;
    }
}
