package com.company;

import javafx.util.Pair;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
    private ArrayList<User> users = new ArrayList<User>();
    private User currentUser;
    private Scanner input = new Scanner(System.in);

    public void Run(){
        System.out.println("Lets play Cows and Bulls.");
        Bot bot = new Bot();
        String output;
        initUser();
        initGame(bot);
        do{
            var inputStr = input.nextLine();
            System.out.println(bot.handleInput(inputStr, currentUser));
        } while (!bot.gameOver);
    }

//    public static void main() {
//        ApiContextInitializer.init(); // Инициализируем апи
//        TelegBot telegBot = new TelegBot("CowsAndBulls", "574223491:AAE0KkuaDSiXPNvlr9SudceuulT1O93K1uk");
//        TelegramBotsApi botapi = new TelegramBotsApi();
//        try {
//            botapi.registerBot(telegBot);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    private void initUser(){
        System.out.println("Hello, write yor name.");
        currentUser = new User(input.nextLine());
        users.add(currentUser);
    }

    private void initGame(Bot bot){
        Saver saver = new Saver();
        if (!saver.checkUserSave(currentUser)) {
            System.out.println("Input number of digits");
            return;
        }
        System.out.println("There is a save with the same name, do you want continue saved game? Y/N");
        if (shouldLoadSave()){
            loadSave(bot, saver);
        }
        else{
            saver.deleteExistingSave(currentUser);
            System.out.println("Input number of digits");
        }
    }

    private void loadSave(Bot bot, Saver saver){
        SaveInformation information = saver.parseExistingSave(currentUser);
        currentUser.setTries(information.getTries());
        bot.loadSave(information);
        System.out.println(information.getLogInfo());
    }

    private boolean shouldLoadSave(){
        String str = input.nextLine();
        return str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes");
    }
}


//Maven, Google guice