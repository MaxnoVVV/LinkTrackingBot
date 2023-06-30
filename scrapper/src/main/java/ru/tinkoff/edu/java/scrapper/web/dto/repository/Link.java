package ru.tinkoff.edu.java.scrapper.web.dto.repository;

import java.time.OffsetDateTime;


public record Link(long linkId, String link, long trackingUser, OffsetDateTime lastCheck) {

  long getlinkId() {
    return linkId;
  }

  public String getLink() {
    return link;
  }

  public long getTrackingUser() {
    return trackingUser;
  }

  public OffsetDateTime getLastCheck() {
    return lastCheck;
  }
}
