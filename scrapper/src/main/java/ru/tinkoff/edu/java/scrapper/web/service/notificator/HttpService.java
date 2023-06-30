package ru.tinkoff.edu.java.scrapper.web.service.notificator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.client.BotClient;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;

@RequiredArgsConstructor
public class HttpService implements SendUpdatesService {
    private final BotClient botClient;
    @Override
    public ResponseEntity sendUpdate(LinkUpdateRequest Update) {
        return botClient.sendUpdate(Update);
    }
}
