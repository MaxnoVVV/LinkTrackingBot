package ru.tinkoff.edu.java.scrapper.web.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.web.entity.Link;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT l FROM Link l WHERE l.link = ?1 AND l.trackinguser = ?2")
    List<Link> findByLinkAndTracking_user(String link, long tracking_user);

    @Query("SELECT l FROM Link l WHERE l.trackinguser = ?1")
    List<Link> findAllByTracking_user(long tracking_user);

    void deleteAllByLinkAndTrackinguser(String link, long tracking_user);
}
