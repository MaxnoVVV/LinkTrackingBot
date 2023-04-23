package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.BaseParser;
import ru.tinkoff.edu.java.GitHubParser;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.StackOverFlowParser;

@Configuration
public class ParserConfig {
    @Bean
    public Parser parser()
    {
        return Parser.link(new BaseParser(),new GitHubParser(),new StackOverFlowParser());
    }
}
