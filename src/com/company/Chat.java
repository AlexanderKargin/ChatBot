package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
    public void Run(){
        Scanner input = new Scanner(System.in);
        Bot bot = new Bot();
        String output;
        System.out.println("Hello, lets play Cows and Bulls. Input the number of digits(4 or 5)");
        do{
            int number = input.nextInt();
            bot.readInput(number);
            output = bot.makeAnswer();
            System.out.println(output);
        } while (!bot.gameOver);
    }
}
