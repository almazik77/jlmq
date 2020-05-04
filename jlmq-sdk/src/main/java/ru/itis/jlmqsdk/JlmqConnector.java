package ru.itis.jlmqsdk;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.Synchronized;

import javax.websocket.ContainerProvider;
import java.net.URI;

public class JlmqConnector {

    private final ClientEndpointImpl clientEndpointImpl;
    private JlmqConsumer consumer;
    private ObjectMapper objectMapper;

    @SneakyThrows
    public JlmqConnector(URI uri) {
        clientEndpointImpl = new ClientEndpointImpl(this);
        ContainerProvider.getWebSocketContainer().connectToServer(clientEndpointImpl, uri);
        this.objectMapper = new ObjectMapper();
    }

    public JlmqProducer producer(String queue) {
        return new JlmqProducer(this, queue);
    }


    public JlmqConsumer consumer(String queue, Handler handler) {
        consumer = new JlmqConsumer(this, handler, queue);
        return consumer;
    }

    @Synchronized
    @SneakyThrows
    void send(Object message) {
        System.out.println(objectMapper.writeValueAsString(message));
        clientEndpointImpl.send(objectMapper.writeValueAsString(message));
    }

    public JlmqConsumer getConsumer() {
        return consumer;
    }

}
