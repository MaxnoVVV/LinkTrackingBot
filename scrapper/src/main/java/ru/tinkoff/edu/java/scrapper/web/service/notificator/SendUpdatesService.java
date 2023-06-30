package ru.tinkoff.edu.java.scrapper.web.service.notificator;

import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;

public interface SendUpdatesService {
    public ResponseEntity sendUpdate(LinkUpdateRequest Update);
}
