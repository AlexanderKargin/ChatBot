package com.company;

import javafx.util.Pair;

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
        System.out.println(bot.makeAnswer(currentUser));
        do{
            var inputStr = input.nextLine();
            bot.readInput(inputStr, currentUser);
            output = bot.makeAnswer(currentUser);
            System.out.println(output);
            System.out.println(bot.toString());
        } while (!bot.gameOver);
    }

    private void initUser(){
        System.out.println("Hello, write yor name.");
        currentUser = new User(input.nextLine());
        users.add(currentUser);
    }

    private void initGame(Bot bot){
        Saver saver = new Saver();
        if (!saver.checkUserSave(currentUser)) {
            return;
        }
        System.out.println("There is a save with the same name, do you want continue saved game? Y/N");
        if (shouldLoadSave()){
            loadSave(bot, saver);
        }
        saver.deleteExistingSave(currentUser);
    }

    private void loadSave(Bot bot, Saver saver){
        SaveInformation information = saver.parseExistingSave(currentUser);
        currentUser.setTries(information.getTries());
        bot.loadSave(information);
        System.out.println(information.getLogInfo());
    }

    private boolean shouldLoadSave(){
        String str = input.nextLine();
        if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")){
            return true;
        }
        else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")){
            return false;
        }
        return false;
    }
}
