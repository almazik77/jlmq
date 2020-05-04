package ru.itis.jlmqsdk;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Setter;

@Builder(access = AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class JlmqProducer {
    private JlmqConnector connector;
    private String queueName;

    public void toQueue(String name) {
        this.queueName = name;
    }


    public void send(Object message) {
        Message message1 = Message.builder()
                .command("send")
                .queueName(queueName)
                .body(message)
                .build();

        connector.send(message1);
    }

}
