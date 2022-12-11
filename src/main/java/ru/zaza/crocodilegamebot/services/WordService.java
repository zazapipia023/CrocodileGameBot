package ru.zaza.crocodilegamebot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.crocodilegamebot.entities.Word;
import ru.zaza.crocodilegamebot.repositories.WordsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WordService {
    private final WordsRepository wordsRepository;

    @Autowired
    public WordService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public Word findOne(int id) {
        Optional<Word> foundWord = wordsRepository.findById(id);
        return foundWord.orElse(null);
    }

    @Transactional
    public void save(Word word) {
        wordsRepository.save(word);
    }

    @Transactional
    public void delete(int id) {
        wordsRepository.deleteById(id);
    }
}
