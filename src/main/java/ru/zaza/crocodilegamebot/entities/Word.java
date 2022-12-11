package ru.zaza.crocodilegamebot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "word")
@Getter
@Setter
public class Word {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "word")
    private String word;

    public Word() {

    }

    public Word(String word) {
        this.word = word;
    }
}
