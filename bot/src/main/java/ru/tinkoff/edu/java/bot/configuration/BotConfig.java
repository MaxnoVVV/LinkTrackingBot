package ru.tinkoff.edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.web.bot.commands.*;
import ru.tinkoff.edu.java.bot.web.client.ScrapperClient;

@Configuration
public class BotConfig {

    @Autowired
    ApplicationConfig config;

    @Bean
    public Command commandProccessor()
    {
        return  Command.build(new ScrapperClient(),new HelpCommand(),new ListCommand(),new StartCommand(),new TrackCommand(),new UntrackCommand());
    }


    @Bean
    public TelegramBot telegramBot()
    {
        return new TelegramBot(config.TOKEN());
    }
}
