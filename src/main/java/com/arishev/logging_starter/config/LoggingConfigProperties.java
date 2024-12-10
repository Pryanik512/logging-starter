package com.arishev.logging_starter.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties("my-logging")
public class LoggingConfigProperties {

    private String enabled;

    private String level;
}
