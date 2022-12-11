package ru.zaza.crocodilegamebot.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.zaza.crocodilegamebot.services.ChatService;
import ru.zaza.crocodilegamebot.services.WordService;

@Component
public class CrocodileGame {

    private final WordService wordService;
    private final ChatService chatService;
    // TODO: UserService

    @Autowired
    public CrocodileGame(WordService wordService, ChatService chatService) {
        this.wordService = wordService;
        this.chatService = chatService;
    }

    // TODO: Make game logic
    public SendMessage handleMessage(Message message) {
        boolean isStarted = chatService.findOne(message.getChatId()).isStarted();

        if (message.getText().equals("/start") && !isStarted) {

        }
        return null;
    }
}
