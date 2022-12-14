package ru.zaza.crocodilegamebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.zaza.crocodilegamebot.handler.MessageHandler;

@Component
public class TelegramFacade {

    private final MessageHandler messageHandler;

    @Autowired
    public TelegramFacade(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return messageHandler.handleCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if(message.hasText()) {
                return messageHandler.handleMessage(message);
            }
        }
        return null;
    }
}
