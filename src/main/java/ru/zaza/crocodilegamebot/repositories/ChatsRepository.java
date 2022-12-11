package ru.zaza.crocodilegamebot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.crocodilegamebot.entities.Chat;

@Repository
public interface ChatsRepository extends JpaRepository<Chat, Long> {
}
