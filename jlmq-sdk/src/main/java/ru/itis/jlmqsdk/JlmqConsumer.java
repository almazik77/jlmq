package ru.itis.jlmqsdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JlmqConsumer {
    private JlmqConnector connector;
    private Handler handler;
    private ObjectMapper objectMapper;

    protected JlmqConsumer(JlmqConnector connector, Handler handler, String name) {
        this.connector = connector;
        this.handler = handler;
        this.objectMapper = new ObjectMapper();
        this.connector.send(Message.builder()
                .command("subscribe")
                .queueName(name)
                .build());
    }


    @SneakyThrows
    void handle(String message) {
        Message message1 = objectMapper.readValue(message, Message.class);

        Message messageToSend = Message.builder()
                .messageId(message1.getMessageId())
                .build();

        messageToSend.setCommand("accepted");
        connector.send(messageToSend);

       // handler.handle(messageToSend.getBody());

        messageToSend.setCommand("completed");
        connector.send(messageToSend);

    }


}
