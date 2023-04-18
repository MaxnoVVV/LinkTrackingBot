package ru.tinkoff.edu.java.testing;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.web.bot.commands.*;
import ru.tinkoff.edu.java.bot.web.client.BotClient;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.LinkResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ListLinksResponse;

import static reactor.core.publisher.Mono.when;
@ExtendWith(MockitoExtension.class)
public class Tests {
    @Mock
    Update update = new Update();

    @Mock
    Message message = new Message();

    @Mock
    Chat chat = new Chat();
    @Mock
    BotClient client = new BotClient();


    @SneakyThrows
    @Test
    void ListCommandTest() {

        String link1 = "https://github.com/MaxnoVVV/TinkoffBot";
        String link2 = "https://github.com/pengrad/java-telegram-bot-api/";

        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/list");
        Mockito.when(client.getLinks(Mockito.anyLong())).thenReturn(new ListLinksResponse(2,new LinkResponse[]{new LinkResponse(1,link1),new LinkResponse(2,link2)}));

        Command command = Command.build(client, new HelpCommand(),new StartCommand(),new ListCommand(),new TrackCommand(),new UntrackCommand());

        CommandResponse request = command.proccess(update);

        Assertions.assertInstanceOf(SuccessCommandResponse.class,request);

        Assertions.assertEquals(((SuccessCommandResponse)request).text(),String.format("%s\n%s\n",link1,link2));

        Mockito.when(client.getLinks(Mockito.anyLong())).thenReturn(new ListLinksResponse(0,null));
        request = command.proccess(update);

        Assertions.assertEquals(((SuccessCommandResponse)request).text(),"Нет отслеживаемых ссылок");
    }



}
