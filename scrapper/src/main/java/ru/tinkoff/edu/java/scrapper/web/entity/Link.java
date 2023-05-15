package ru.tinkoff.edu.java.scrapper.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "links")
@Getter
@Setter
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long link_id;

    @Column(name = "link")
    private String link;
    @Column(name = "trackingUser")
    private long trackinguser;

    @Column(name = "lastCheck")
    private OffsetDateTime last_check;

}
