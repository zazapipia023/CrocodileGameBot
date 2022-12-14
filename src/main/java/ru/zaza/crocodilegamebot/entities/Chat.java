package ru.zaza.crocodilegamebot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "chat")
@Getter
@Setter
public class Chat {

    @Id
    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "is_started")
    private boolean isStarted;

    @Column(name = "explaining_person")
    private Long explainingPerson;

    @Column(name = "word")
    private String word;

    public Chat(long chatId) {
        this.chatId = chatId;
    }

    public Chat() {

    }
}
