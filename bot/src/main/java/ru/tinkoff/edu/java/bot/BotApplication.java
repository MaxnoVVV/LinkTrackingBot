package ru.tinkoff.edu.java.bot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.BotConfig;
import ru.tinkoff.edu.java.bot.web.bot.Bot;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@SpringBootApplication
@Import(BotConfig.class)
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
        System.out.println(config.TOKEN());
        System.out.println("6140845705:AAHlkxMBdRDwkc0EqJtfqJfcCxJIk-pVcQ0");
        Bot bot = ctx.getBean(Bot.class);
        bot.start();
        }
        }