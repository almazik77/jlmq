package ru.itis.jlmq.handlers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.jlmq.services.MessageService;
import ru.itis.jlmq.services.SubscriptionService;

import java.util.Map;

@Component
public class JlmqMessageHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map messageAsMap = new Gson().fromJson(message.getPayload(), Map.class);
        for (Object key : messageAsMap.keySet()) {
            System.out.println(key + " " + messageAsMap.get(key));
        }
        if (messageAsMap.get("command") == null) {
            throw new IllegalStateException("no commands");
        }
        switch (messageAsMap.get("command").toString()) {
            case "send" -> {
                messageService.send(messageAsMap.get("queueName").toString(), messageAsMap.get("body"));
            }
            case "subscribe" -> {
                subscriptionService.subscribe(session, messageAsMap.get("queueName").toString());
                messageService.findMessageToSend(session);
            }
            case "accepted" -> {
                messageService.accepted(session, messageAsMap.get("messageId").toString());
            }
            case "completed" -> {
                messageService.completed(session, messageAsMap.get("messageId").toString());
            }
            default -> throw new IllegalStateException("unknown command");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        subscriptionService.unsubscribe(session);
    }
}

