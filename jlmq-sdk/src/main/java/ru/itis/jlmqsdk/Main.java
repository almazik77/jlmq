package ru.itis.jlmqsdk;

import lombok.SneakyThrows;

import java.net.URI;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        URI uri = new URI("ws://localhost:8081/queue");

        JlmqConnector connector = new JlmqConnector(uri);

        JlmqProducer producer = connector.producer("1234124");


        producer.send("asdsd");
        producer.send("99999");
        producer.send("88888");
        producer.send("77777");
        producer.send("66666");

        connector.consumer("1234124", body -> {
            System.out.println(body);
            Thread.sleep(5000);
        });

        Thread.sleep(20000);

    }
}
