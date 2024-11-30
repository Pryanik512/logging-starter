package com.arishev.logging_starter.config;


import com.arishev.logging_starter.aspect.TaskAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {


    @Bean
    public TaskAspect taskAspect() {
        return new TaskAspect();
    }
}
