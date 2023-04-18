package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.RemoveLinkRequest;

@Slf4j
public class UntrackCommand extends Command {
    @Override
    public CommandResponse proccess(Update update) {
        if (update.message().text().equals("/untrack")) {
            log.info(String.format("Update %d with %s message is /untrack command", update.updateId(), update.message().text()));

            return new SuccessCommandResponse("Отправьте ссылку для отмены отслеживания", true);
        } else {
            log.info(String.format("Update %d with %s message is not /untrack command", update.updateId(), update.message().text()));

            return next(update);
        }
    }

    public static SendMessage stopTrack(Update update) {
        ResponseEntity<?> response = client.deleteLink(update.message().chat().id(), new RemoveLinkRequest(update.message().text()));
        if (response.getStatusCode() == HttpStatus.OK) {
            return new SendMessage(update.message().chat().id(), "Ссылка удалена");
        } else {
            return new SendMessage(update.message().chat().id(), "Ссылка некорректна или не отслеживается");
        }

    }
}

