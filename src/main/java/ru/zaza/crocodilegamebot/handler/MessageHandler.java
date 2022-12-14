package ru.zaza.crocodilegamebot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class MessageHandler {

    @Value("${admin_id}")
    private long adminId;
    private final GroupMessageHandler groupMessageHandler;
    private final AdminMessageHandler adminMessageHandler;
    private final UserMessageHandler userMessageHandler;

    @Autowired
    public MessageHandler(GroupMessageHandler groupMessageHandler, AdminMessageHandler adminMessageHandler, UserMessageHandler userMessageHandler) {
        this.groupMessageHandler = groupMessageHandler;
        this.adminMessageHandler = adminMessageHandler;
        this.userMessageHandler = userMessageHandler;
    }

    public SendMessage handleMessage(Message message) {
        if (message.isSuperGroupMessage()) {
            return groupMessageHandler.handleMessage(message);
        }

        if (message.getChatId() == adminId) {
            return adminMessageHandler.handleMessage(message);
        }

        return userMessageHandler.handleMessage(message);
    }

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        return groupMessageHandler.handleCallbackQuery(callbackQuery);
    }
}
