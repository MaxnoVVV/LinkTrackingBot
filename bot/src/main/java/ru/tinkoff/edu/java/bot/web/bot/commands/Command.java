package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.web.client.ScrapperClient;

public abstract class Command {

    protected static ScrapperClient client;

    public static Command build(ScrapperClient client, Command first, Command... chain) {
        Command head = first;
        Command.client = client;

        for (Command nextCommand : chain) {
            head.next = nextCommand;
            head = nextCommand;
        }

        head.next = null;
        return first;
    }

    Command next;

    public CommandResponse proccess(Update update) {
        return null;
    }

    protected CommandResponse next(Update update) {
        if (next == null) {
            return null;
        }
        return next.proccess(update);
    }
}
