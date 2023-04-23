package ru.tinkoff.edu.java.bot.web.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.configuration.BotConfig;
import ru.tinkoff.edu.java.bot.web.bot.commands.*;
import ru.tinkoff.edu.java.bot.web.client.ScrapperClient;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Import(BotConfig.class)
public class Bot implements AutoCloseable {
    @Autowired
    private TelegramBot bot;

    @Autowired
    private  Command commandProcessor;

    public void addCommands() //bonus Task
    {
        log.info("Adding commands");
        bot.execute(new SetMyCommands(new BotCommand("/help", "Список команд"),
                new BotCommand("/track", "Начать отслеживание ссылки"),
                new BotCommand("/untrack", "Прекратить отслеживание ссылки"),
                new BotCommand("/list", "Вывести все отслеживаемые ссылки"),
                new BotCommand("/start", "Начать работу с ботом")));
        log.info("Commands added");
    }

    public void start() {
        log.info(String.format("Bot %s started", bot.getToken()));
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for (Update update : updates) {
                    log.info("Got new update");
                    if(update.updateId() == 795769925 || update.updateId() == 795769927) continue;
                    processUpdate(update);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    @Override
    public void close() throws Exception {
        bot.removeGetUpdatesListener();
    }

    private void processCommand(Update update) {
        CommandResponse result = commandProcessor.proccess(update);
        if (result == null) {
            log.info(String.format("Unknown command for %d", update.updateId()));
            bot.execute(new SendMessage(update.message().chat().id(), "Неизвестная команда"));
        } else if (result instanceof SuccessCommandResponse) {

            SendMessage request = new SendMessage(update.message().chat().id(), ((SuccessCommandResponse) result).text());
            if (((SuccessCommandResponse) result).isForceReply()) {
                request.replyMarkup(new ForceReply());
            }
            log.info(String.format("Executing %d update command", update.updateId()));
            bot.execute(request);
        } else {
            log.info(String.format("Update %d command did not processed successfully", update.updateId()));
        }
    }

    public void processUpdate(Update update) {

        log.info(String.format("Update %d processing", update.updateId()));

        if (update.message() != null
                && update.message().entities() != null && Arrays.stream(update.message().entities())
                .anyMatch(entity -> {
                    return entity.type() == MessageEntity.Type.bot_command;
                })) {
            log.info(String.format("Update %d is command, processing", update.updateId()));

            processCommand(update);

            log.info(String.format("Update %d command, processed", update.updateId()));

        } else if (update.message() != null
                && update.message().replyToMessage() != null
                && update.message().replyToMessage().from().isBot()) {
            log.info(String.format("Update %d is link", update.updateId()));

            if (update.message().replyToMessage().text().equals("Отправьте ссылку для отслеживания")) {
                bot.execute(TrackCommand.startTrack(update));
            } else if (update.message().replyToMessage().text().equals("Отправьте ссылку для отмены отслеживания")) {
                bot.execute(UntrackCommand.stopTrack(update));
            }
        } else {
            log.info(String.format("No action for %d update", update.updateId()));
        }
    }
}
