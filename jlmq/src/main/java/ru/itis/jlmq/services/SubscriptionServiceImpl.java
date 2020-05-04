package ru.itis.jlmq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import ru.itis.jlmq.models.Queue;
import ru.itis.jlmq.repositories.QueueRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final Map<Queue, WebSocketSession> subscribers = new HashMap<>();

    @Autowired
    private QueueRepository queueRepository;

    @Override
    public void subscribe(WebSocketSession session, String queueName) {
        Optional<Queue> queue = queueRepository.findByName(queueName);
        if (queue.isPresent()) {
            subscribers.put(queue.get(), session);
            session.getAttributes().put("queue", queue.get());
        }
    }

    @Override
    public void unsubscribe(WebSocketSession session) {
        subscribers.remove(session.getAttributes().get("queue"));
    }

    @Override
    public Optional<WebSocketSession> subscriber(Queue queue) {
        return Optional.ofNullable(subscribers.get(queue));
    }
}
