package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
    public void Run(){
        Scanner input = new Scanner(System.in);
        Bot bot = new Bot();
        String output;
        System.out.println("Hello, lets play Cows and Bulls.");
        System.out.println(bot.makeAnswer());
        do{
            int number = input.nextInt();
            bot.readInput(number);
            output = bot.makeAnswer();
            System.out.println(output);
        } while (!bot.gameOver);
    }
}
