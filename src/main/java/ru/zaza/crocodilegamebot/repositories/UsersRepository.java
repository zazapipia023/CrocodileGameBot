package ru.zaza.crocodilegamebot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaza.crocodilegamebot.entities.Player;

@Repository
public interface UsersRepository extends JpaRepository<Player, Long> {

}
