package com.company;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class TelegBot extends TelegramLongPollingBot {
    private String NAME;
    private String TOKEN;
    private Chat chat;

    public TelegBot(String name, String token, DefaultBotOptions botOptions){
        super(botOptions);
        NAME = name;
        TOKEN = token;
        chat = new Chat();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        //chat.Run(this, update.getMessage().getChatId().toString());
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
