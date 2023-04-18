package ru.tinkoff.edu.java.scrapper.web.dto.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


public record Link(int link_id, String link, int tracking_user, OffsetDateTime last_check) {
    int getLink_id()
    {
        return link_id;
    }

    public String getLink()
    {
        return  link;
    }

    public int getTracking_user()
    {
        return tracking_user;
    }

    public OffsetDateTime getLast_check()
    {
        return  last_check;
    }
}