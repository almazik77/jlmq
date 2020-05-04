package ru.itis.jlmq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.jlmq.models.Queue;

import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    Optional<Queue> findByName(String name);
}
