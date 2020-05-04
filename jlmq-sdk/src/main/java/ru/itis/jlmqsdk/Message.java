package ru.itis.jlmqsdk;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String queueName;
    private String command;
    private String messageId;
    private Object body;

}
