package ru.tinkoff.edu.java.bot.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.web.bot.Bot;
import ru.tinkoff.edu.java.bot.web.dto.LinkUpdateRequest;
@RequiredArgsConstructor
@Service
public class UpdateService {
    private final Bot bot;
    public void proccessUpdate(LinkUpdateRequest Update)
    {
        bot.receiveUpdate(Update);
    }
}
