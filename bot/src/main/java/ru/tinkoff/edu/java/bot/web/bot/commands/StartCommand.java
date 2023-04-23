package ru.tinkoff.edu.java.bot.web.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class StartCommand extends Command {
    @Override
    public CommandResponse proccess(Update update) {
        ResponseEntity<?> response;

        if(update.message().text().trim().equals("/start"))
        {
            try {
                response = client.registerChat(update.message().chat().id());
                log.info("response from start command is " + response);
                if (response.getStatusCode().is2xxSuccessful()) {
                    return new SuccessCommandResponse("Введите /help, чтобы узнать, как пользоваться ботом!", false);
                } else {
                    return new SuccessCommandResponse("Вы уже зарегестрированы в боте", false);
                }
            } catch (Exception e) {
                log.info("error in startcommand");
                log.info(e.getMessage());
                for(var ex : e.getStackTrace())
                {
                    log.info(ex.toString());
                }
                log.info(e.toString());

                return new ErrorCommandResponse();
            }
        }
        else
        {
            return next(update);
        }

    }
}
