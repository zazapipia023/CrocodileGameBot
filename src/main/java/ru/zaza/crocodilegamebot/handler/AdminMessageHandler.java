package ru.zaza.crocodilegamebot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.zaza.crocodilegamebot.entities.Word;
import ru.zaza.crocodilegamebot.services.ChatService;
import ru.zaza.crocodilegamebot.services.WordService;

@Component
public class AdminMessageHandler {

    private final WordService wordService;
    private final ChatService chatService;

    @Autowired
    public AdminMessageHandler(WordService wordService, ChatService chatService) {
        this.wordService = wordService;
        this.chatService = chatService;
    }

    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        String messageText = message.getText();

        if (messageText.matches("add [А-Яа-я]+")) {
            wordService.save(new Word(messageText.toLowerCase().substring(4)));
            sendMessage.setText("Word added");
            return sendMessage;
        }

        sendMessage.setText("Unknown command");

        return sendMessage;
    }
}
