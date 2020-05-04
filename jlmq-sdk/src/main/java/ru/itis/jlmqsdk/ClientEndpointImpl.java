package ru.itis.jlmqsdk;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class ClientEndpointImpl {
    private JlmqConnector connector;
    private Session session;

    public ClientEndpointImpl(JlmqConnector connector) {
        this.connector = connector;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        JlmqConsumer consumer = connector.getConsumer();
        System.out.println(message);
        consumer.handle(message);
    }

    public void send(String text) {
        session.getAsyncRemote().sendText(text);
    }

}
