package ru.itis.jlmq.services;

import ru.itis.jlmq.dto.QueueDto;

public interface QueueService {
    void createQueue(QueueDto queueDto);
}
