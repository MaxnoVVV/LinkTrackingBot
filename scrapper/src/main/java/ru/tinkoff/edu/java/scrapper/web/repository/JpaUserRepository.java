package ru.tinkoff.edu.java.scrapper.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.web.entity.User;

import java.util.List;

@Repository
public interface JpaUserRepository extends JpaRepository<User,Long> {
    List<User> findAllByTgId(long tgId);

    void deleteAllByTgId(long tgId);
}
