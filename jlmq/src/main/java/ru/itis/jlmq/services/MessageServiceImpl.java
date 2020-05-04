package ru.itis.jlmq.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.itis.jlmq.models.Message;
import ru.itis.jlmq.models.Queue;
import ru.itis.jlmq.repositories.MessageRepository;
import ru.itis.jlmq.repositories.QueueRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public void send(String queueName, Object body) {
        Queue queue = queueRepository.findByName(queueName).orElseThrow(IllegalArgumentException::new);
        Message message = Message.builder()
                .queue(queue)
                .messageId(UUID.randomUUID().toString())
                .status(Message.Status.RECEIVED)
                .body(body.toString())
                .build();

        messageRepository.save(message);

        subscriptionService.subscriber(queue).ifPresent(session -> send(session, message));
    }

    @SneakyThrows
    @Override
    public void findMessageToSend(WebSocketSession session) {
        Queue queue = (Queue) session.getAttributes().get("queue");
        Optional<Message> message = messageRepository.findFirstByQueueAndStatus(queue, Message.Status.RECEIVED);

        message.ifPresent(value -> send(session, value));
    }

    @Override
    public void accepted(WebSocketSession session, String messageId) {
        messageRepository.findByMessageId(messageId)
                .ifPresent(message -> {
                    message.setStatus(Message.Status.ACCEPTED);
                    messageRepository.save(message);
                });
    }

    @Override
    public void completed(WebSocketSession session, String messageId) {
        messageRepository.findByMessageId(messageId)
                .ifPresent(message -> {
                    message.setStatus(Message.Status.COMPLETED);
                    messageRepository.save(message);
                });
        findMessageToSend(session);
    }


    @SneakyThrows
    private void send(WebSocketSession session, Message body) {
        Map<String, Object> messageAsMap = new HashMap<>();
        messageAsMap.put("command", "receive");
        if (body.getMessageId() != null)
            messageAsMap.put("messageId", body.getMessageId());
        messageAsMap.put("body", body);
        System.out.println(objectMapper.writeValueAsString(messageAsMap));
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageAsMap)));
    }
}
