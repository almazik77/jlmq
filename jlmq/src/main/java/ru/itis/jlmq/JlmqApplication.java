package ru.itis.jlmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JlmqApplication {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(JlmqApplication.class, args);
    }

}
