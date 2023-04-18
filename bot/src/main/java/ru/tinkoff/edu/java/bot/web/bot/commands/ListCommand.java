package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.LinkResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ListLinksResponse;

@Slf4j
public class ListCommand extends Command {

    @Override
    public CommandResponse proccess(Update update) {
        if (update.message().text().equals("/list")) {
            log.info(String.format("Update %d with %s message is /list command", update.updateId(), update.message().text()));
            ListLinksResponse links;
            try {
                links = client.getLinks(update.message().chat().id());
            } catch (Exception e) {
                log.info(("Command /list can not be processed"));
                return new ErrorCommandResponse();
            }
            if (links.size() == 0) {
                return new SuccessCommandResponse("Нет отслеживаемых ссылок", false);
            } else {
                String response = "";
                for (LinkResponse link : links.links()) {
                    response += link.url() + "\n";
                }
                return new SuccessCommandResponse(response, false);
            }

        } else {
            log.info(String.format("Update %d with %s message is not /list command", update.updateId(), update.message().text()));

            return next(update);
        }
    }
}
