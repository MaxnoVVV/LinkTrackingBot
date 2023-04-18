package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StartCommand extends Command {
    @Override
    public CommandResponse proccess(Update update) {
        ResponseEntity<?> response;
        try {
            response = client.registerChat(update.message().chat().id());
            if (response.getStatusCode() == HttpStatus.OK) {
                return new SuccessCommandResponse("Введите /help, чтобы узнать, как пользоваться ботом!", false);
            } else {
                return new SuccessCommandResponse("Вы уже зарегестрированы в боте", false);
            }
        } catch (Exception e) {
            return new ErrorCommandResponse();
        }


    }
}
