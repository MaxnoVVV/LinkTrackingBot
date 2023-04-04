package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelpCommand extends Command {
    private final String message = "/start -- зарегистрировать пользователя\n" +
            "/help -- вывести окно с командами\n" +
            "/track -- начать отслеживание ссылки\n" +
            "/untrack -- прекратить отслеживание ссылки\n" +
            "/list -- показать список отслеживаемых ссылок";

    @Override
    public CommandResponse proccess(Update update) {
        if (update.message().text().equals("/help")) {
            log.info(String.format("Update %d with %s message is /help command", update.updateId(), update.message().text()));
            return new SuccessCommandResponse(message, false);
        } else {
            log.info(String.format("Update %d with %s message is not /help command", update.updateId(), update.message().text()));
            return next(update);
        }
    }
}
