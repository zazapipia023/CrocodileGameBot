package ru.zaza.crocodilegamebot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zaza.crocodilegamebot.entities.Player;
import ru.zaza.crocodilegamebot.repositories.UsersRepository;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Player findOne(long id) {
        Optional<Player> foundUser = usersRepository.findById(id);
        return foundUser.orElse(new Player(0));
    }

    @Transactional
    public void save(Player player) {
        usersRepository.save(player);
    }

}
