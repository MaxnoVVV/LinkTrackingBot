package ru.tinkoff.edu.java.scrapper.web.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class LinkMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Link(rs.getInt("linkId"), rs.getString("link"), rs.getInt("trackingUser"), OffsetDateTime.ofInstant(rs.getTimestamp("lastCheck").toInstant(), ZoneId.systemDefault()));
    }
}
