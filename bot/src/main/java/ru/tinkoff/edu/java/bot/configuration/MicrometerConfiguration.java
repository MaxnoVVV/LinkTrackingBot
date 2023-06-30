package ru.tinkoff.edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {
  @Bean
  MeterRegistry registry()
  {
    return new SimpleMeterRegistry();
  }
  @Bean
  Counter counter( MeterRegistry registry)
  {
    return Counter.builder("updates_processed_bot").register(registry);
  }
}
