package ru.zaza.crocodilegamebot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.zaza.crocodilegamebot.entities.Chat;
import ru.zaza.crocodilegamebot.game.CrocodileGame;
import ru.zaza.crocodilegamebot.services.ChatService;
import ru.zaza.crocodilegamebot.services.WordService;

@Component
public class GroupMessageHandler {

    private final WordService wordService;
    private final ChatService chatService;
    private final CrocodileGame game;

    @Autowired
    public GroupMessageHandler(WordService wordService, ChatService chatService, CrocodileGame game) {
        this.wordService = wordService;
        this.chatService = chatService;
        this.game = game;
    }

    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());


        Chat currentChat = chatService.findOne(message.getChatId());

        if (currentChat.getChatId() == 0) {
            chatService.save(new Chat(message.getChatId()));
            sendMessage.setText("Я запомнил чат, можете играть");
            return sendMessage;
        }





        return sendMessage;
    }
}
