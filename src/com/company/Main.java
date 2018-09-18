package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
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
        Bot createdNumber = new Bot(numberOfDigits);
        System.out.println(createdNumber.toString());
        System.out.println("Теперь высри само число(в нем должно быть столько цифр, сколько сам указал");
        int guess = input.nextInt();
        while (Integer.toString(guess).length() != numberOfDigits){
            System.out.println("Ты точно тупой");
            guess = input.nextInt();
        }
        Pair<Integer, Integer> cowsAndBulls = createdNumber.checkCowsAndBulls(guess);
        System.out.println(String.format("Коров: %d. Быков %d", cowsAndBulls.getKey(), cowsAndBulls.getValue()));
    }

    public boolean areThereRepeats(int number){
        String stringNumber = Integer.toString(number);
        ArrayList<String> numbers;
        for (var i = 0; i < stringNumber.length(); i++){

        }
        return true;
    }
}
