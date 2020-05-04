package ru.itis.jlmq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.jlmq.dto.QueueDto;
import ru.itis.jlmq.models.Queue;
import ru.itis.jlmq.repositories.QueueRepository;

@Service
public class QueueServiceImpl implements QueueService {
    @Autowired
    private QueueRepository queueRepository;


    @Override
    public void createQueue(QueueDto queueDto) {
        Queue queue = Queue.builder()
                .name(queueDto.getName())
                .build();
        queueRepository.save(queue);
    }
}
