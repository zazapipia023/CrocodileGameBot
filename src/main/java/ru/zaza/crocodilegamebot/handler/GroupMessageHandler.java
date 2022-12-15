package ru.zaza.crocodilegamebot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.zaza.crocodilegamebot.entities.Chat;
import ru.zaza.crocodilegamebot.enums.GameState;
import ru.zaza.crocodilegamebot.services.ChatService;
import ru.zaza.crocodilegamebot.services.UserService;
import ru.zaza.crocodilegamebot.services.WordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GroupMessageHandler {

    private final ChatService chatService;
    private final WordService wordService;
    private final UserService userService;

    @Autowired
    public GroupMessageHandler(ChatService chatService, WordService wordService, UserService userService) {
        this.chatService = chatService;
        this.wordService = wordService;
        this.userService = userService;
    }

    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        Chat currentChat = chatService.findOne(message.getChatId());

        if (currentChat.getChatId() == 0) {
            chatService.save(new Chat(message.getChatId()));
            sendMessage.setText("Воздух мимо, можете играть");
            return sendMessage;
        }

        if (currentChat.isStarted()) {
            if (message.getText().equals(currentChat.getWord())
                    && message.getFrom().getId() != currentChat.getExplainingPerson()) {
                currentChat.setWord(null);
                currentChat.setExplainingPerson(null);
                // TODO: Add 1 point to person, who guessed right

                sendMessage.setText(message.getFrom().getFirstName() + " угадал, ахуеть...\n" +
                        "Кто хочет объяснять слово?");
                sendMessage.setReplyMarkup(makeKeyboard(GameState.WAITING));
                return sendMessage;
            }

            if (message.getText().equals("/stop@normCrocoGame_bot")) {
                currentChat.setStarted(false);
                currentChat.setExplainingPerson(null);
                currentChat.setWord(null);
                chatService.save(currentChat);

                sendMessage.setText("Игра остановлена, расход мужики");
                return sendMessage;
            }
        } else {
            if (message.getText().equals("/start@normCrocoGame_bot")) {
                currentChat.setStarted(true);
                chatService.save(currentChat);


                sendMessage.setText("Кто хочет объяснять слово?");
                sendMessage.setReplyMarkup(makeKeyboard(GameState.WAITING));
            }
        }

        return sendMessage;
    }

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        User person = callbackQuery.getFrom();
        Chat currentChat = chatService.findOne(callbackQuery.getMessage().getChatId());

        String callbackData = callbackQuery.getData();

        switch (callbackData) {
            case "EXPLAIN_BUTTON" -> {
                if(currentChat.getExplainingPerson() != null) {
                    return deleteMessage(callbackQuery);
                }

                currentChat.setWord(wordService.findOne(new Random().nextInt(4)).getWord());
                currentChat.setExplainingPerson(person.getId());
                chatService.save(currentChat);

                sendMessage.setText(person.getFirstName() + " объясняет слово, ждите");
                sendMessage.setReplyMarkup(makeKeyboard(GameState.STARTED));
                return sendMessage;
            }
            case "CHECK_WORD" -> {
                if (person.getId().equals(currentChat.getExplainingPerson())) {
                    return makeAnswerCallbackQuery(callbackQuery.getId(), currentChat.getWord());
                }
            }
            case "NEW_WORD" -> {
                if (person.getId().equals(currentChat.getExplainingPerson())) {
                    currentChat.setWord(wordService.findOne(new Random().nextInt(4)).getWord());
                    return makeAnswerCallbackQuery(callbackQuery.getId(), currentChat.getWord());
                }
            }
        }


        return null;
    }

    private InlineKeyboardMarkup makeKeyboard(GameState gameState) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        switch (gameState) {
            case WAITING -> {
                rowInLine.add(makeButton("Я хочу!", "EXPLAIN_BUTTON"));
                rowsInLine.add(rowInLine);
                markup.setKeyboard(rowsInLine);
                return markup;
            }
            case STARTED -> {
                rowInLine.add(makeButton("Посмотреть слово", "CHECK_WORD"));
                rowInLine.add(makeButton("Новое слово", "NEW_WORD"));
                rowsInLine.add(rowInLine);
                markup.setKeyboard(rowsInLine);
                return markup;
            }
        }

        return markup;
    }

    private InlineKeyboardButton makeButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);

        return button;
    }

    private AnswerCallbackQuery makeAnswerCallbackQuery(String id, String word) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(id);
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(word);

        return answerCallbackQuery;
    }

    private DeleteMessage deleteMessage(CallbackQuery callbackQuery) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        deleteMessage.setChatId(callbackQuery.getMessage().getChatId());

        return deleteMessage;
    }
}
