package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import ru.tinkoff.edu.java.bot.web.dto.clients.LinkResponse;
import ru.tinkoff.edu.java.bot.web.dto.clients.ListLinksResponse;

@Slf4j
public class ListCommand extends Command {

    @Override
    public CommandResponse proccess(Update update) {
        if (update.message().text().trim().equals("/list")) {
            log.info(String.format("Update %d with %s message is /list command", update.updateId(), update.message().text()));
            ListLinksResponse links;
            ResponseEntity response = client.getLinks(update.message().chat().id());
            if(response.getStatusCode().is4xxClientError())
            {
                    log.error("Incorrect request params");
                    return new ErrorCommandResponse();
            }
            else if(response.getStatusCode().is5xxServerError())
            {
                    log.error("Caught exception in client");
                    return new ErrorCommandResponse();
            }
            else
            {
                    log.info("List of links received");
                    links = (ListLinksResponse) response.getBody();
                    String listOfLinks = "";

                    if(links.size() == 0)
                    {
                        return new SuccessCommandResponse("Нет отслеживаемых ссылок", false);
                    }

                    for (LinkResponse link : links.links()) {
                        listOfLinks += link.url() + "\n";
                    }

                    return new SuccessCommandResponse(listOfLinks, false);
            }
        } else {
            log.info(String.format("Update %d with %s message is not /list command", update.updateId(), update.message().text()));

            return next(update);
        }
    }
}
