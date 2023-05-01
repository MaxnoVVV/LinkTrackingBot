package ru.tinkoff.edu.java.scrapper.web.dto.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


public record Link(long link_id, String link, long tracking_user, OffsetDateTime last_check) {
    long getLink_id() {
        return link_id;
    }

    public String getLink() {
        return link;
    }

    public long getTracking_user() {
        return tracking_user;
    }

    public OffsetDateTime getLast_check() {
        return last_check;
    }
}
