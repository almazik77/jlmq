package ru.itis.jlmq.services;

import org.springframework.web.socket.WebSocketSession;

public interface MessageService {
    void send(String queueName, Object body);

    void findMessageToSend(WebSocketSession session);

    void accepted(WebSocketSession session, String messageId);

    void completed(WebSocketSession session, String messageId);
}
