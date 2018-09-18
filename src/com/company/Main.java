package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Высри количество цифр в числе(4 или 5)");
        int numberOfDigits = input.nextInt();
        while (numberOfDigits != 4 && numberOfDigits != 5){
            System.out.println("Ты тупой штоли?");
            numberOfDigits = input.nextInt();
        }
        Bot createNumber = new Bot(numberOfDigits);
        System.out.println(createNumber.toString());
        System.out.println("Теперь высри само число(в нем должно быть столько цифр, сколько сам указал");
        int guess = input.nextInt();
        while (Integer.toString(guess).length() != numberOfDigits){
            System.out.println("Ты точно тупой");
            guess = input.nextInt();
        }
        checker(guess, createNumber);
    }

    public static Pair<Integer, Integer> checker(int guessNumber, Bot createdNumber){
        int cows;
        int bulls;

        return null;
    }

    public boolean areThereRepeats(int number){
        String stringNumber = Integer.toString(number);
        ArrayList<String> numbers;
        for (var i = 0; i < stringNumber.length(); i++){

        }
        return true;
    }
}
