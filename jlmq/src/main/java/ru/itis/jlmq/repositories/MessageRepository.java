package ru.itis.jlmq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.jlmq.models.Message;
import ru.itis.jlmq.models.Queue;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByMessageId(String token);

    Optional<Message> findFirstByQueueAndStatus(Queue queue, Message.Status status);
}
