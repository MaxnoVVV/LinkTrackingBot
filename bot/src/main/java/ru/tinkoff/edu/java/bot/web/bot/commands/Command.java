package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.web.client.BotClient;

public abstract class Command {

    protected static BotClient client;

    public static Command build(BotClient client, Command first, Command... chain) {
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
