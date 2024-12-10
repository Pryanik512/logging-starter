package com.arishev.logging_starter.config;


import com.arishev.logging_starter.aspect.TaskAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingConfigProperties.class)
public class LoggingAutoConfig {

    private final LoggingConfigProperties properties;

    public LoggingAutoConfig(LoggingConfigProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(name = "my-logging.enabled", havingValue = "true", matchIfMissing = true)
    public TaskAspect taskAspect() {
        return new TaskAspect(this.properties);
    }
}
