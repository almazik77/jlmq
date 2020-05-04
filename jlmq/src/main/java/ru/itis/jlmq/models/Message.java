package ru.itis.jlmq.models;

import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String messageId;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String body;

    public enum Status {
        RECEIVED, ACCEPTED, COMPLETED;
    }
}
