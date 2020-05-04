package ru.itis.jlmq.services;

import org.springframework.web.socket.WebSocketSession;
import ru.itis.jlmq.models.Queue;

import java.util.Optional;

public interface SubscriptionService {
    void subscribe(WebSocketSession session, String queueName);

    void unsubscribe(WebSocketSession session);

    Optional<WebSocketSession> subscriber(Queue queue);
}
