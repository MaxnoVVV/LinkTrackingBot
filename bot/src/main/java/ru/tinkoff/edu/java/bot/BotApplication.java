package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.BotConfig;
import ru.tinkoff.edu.java.bot.web.bot.Bot;


@SpringBootApplication
@Import(BotConfig.class)
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

  public static void main(String[] args) {
    var ctx = SpringApplication.run(BotApplication.class, args);
    ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
    Bot bot = ctx.getBean(Bot.class);
    bot.start();
  }
}