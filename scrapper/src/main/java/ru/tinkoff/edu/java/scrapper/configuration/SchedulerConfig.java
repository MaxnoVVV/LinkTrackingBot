package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SchedulerConfig {
    @Bean
    public long schedulerIntervalMs(ApplicationConfig config)
    {
        return config.scheduler().interval().toMillis();
    }

}
