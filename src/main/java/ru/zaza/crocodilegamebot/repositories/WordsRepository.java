package ru.zaza.crocodilegamebot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.crocodilegamebot.entities.Word;

@Repository
public interface WordsRepository extends JpaRepository<Word, Integer> {
}
