package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
    private ArrayList<User> users = new ArrayList<User>();

    public void Run(){
        Scanner input = new Scanner(System.in);
        Bot bot = new Bot();
        Saver saver = new Saver();
        String output;
        System.out.println("Hello, write yor name.");
        var currentUser = new User(input.nextLine());
        users.add(currentUser);
//        saver.checkUserLog(currentUser);
        System.out.println("Lets play Cows and Bulls.");
        System.out.println(bot.makeAnswer(currentUser));
        do{
            var inputStr = input.nextLine();
            bot.readInput(inputStr, currentUser);
            output = bot.makeAnswer(currentUser);
            System.out.println(output);
            //System.out.println(bot.toString());
        } while (!bot.gameOver);
        saver.saveHighScore(currentUser);
    }
}
