package com.company;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import kotlin.NoWhenBranchMatchedException;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class TelegBot extends TelegramLongPollingBot {
    private String NAME;
    private String TOKEN;
    private Map<String, User> users = new HashMap<>();
    private Bot bot = new Bot();


    public TelegBot(String name, String token, DefaultBotOptions botOptions){
        super(botOptions);
        NAME = name;
        TOKEN = token;
    }

    private void initUser(String ID, String name){
        User currentUser = new User(name);
        users.put(ID, currentUser);
    }

    private String getHighScores() {
        StringBuilder text = new StringBuilder();
        try (
                FileReader reader = new FileReader("src/com/company/HighScore.txt")) {
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                text.append(scan.nextLine());
                text.append("\n");
            }
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }
        if (text.length() > 0)
            return text.toString().replace("_", "");
        else
            return "There are no scores yet";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String name = update.getMessage().getChat().getUserName();
        String ID = update.getMessage().getChatId().toString();
        User currentUser = users.get(ID);
        String answer;
        if (message.equals("/hs")) {
            String scores = getHighScores();
            sendMsg(ID, scores);
        }
        else if (message.equals("/start") && currentUser == null){
            initUser(ID, name);
            sendMsg(ID, "Let's play Cows and Bulls!");
            sendMsg(ID, "Input the number of digits(4 or 5)" );
        }
        else if (currentUser == null){
            sendMsg(ID, "To start a new game write /start");
        }
        else{
            answer = bot.handleInput(message, users.get(ID));
            sendMsg(ID, answer);
            if (answer.charAt(2) == 'n')
                users.remove(ID);
        }
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
