package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
    public void Run(){
        Scanner input = new Scanner(System.in);
        System.out.println("Введите количество цифр в числе(4 или 5)");
        int numberOfDigits = input.nextInt();
        while (numberOfDigits != 4 && numberOfDigits != 5) {
            System.out.println("Неверное количество");
            numberOfDigits = input.nextInt();
        }
        Bot createdNumber = new Bot(numberOfDigits);
        System.out.println(createdNumber.toString());
        Pair<Integer, Integer> cowsAndBulls;
        do{
            System.out.println("Введите число(в нем должно быть столько цифр, сколько сам указал");
            int guess = input.nextInt();
            while (Integer.toString(guess).length() != numberOfDigits || areThereRepeats(guess)) {
                System.out.println("Некорректное число");
                guess = input.nextInt();
            }
            cowsAndBulls = createdNumber.checkCowsAndBulls(guess);
            System.out.println(String.format("Коров: %d. Быков %d", cowsAndBulls.getKey(), cowsAndBulls.getValue()));
        } while (cowsAndBulls.getValue() < numberOfDigits);
    }

    public boolean areThereRepeats(int number) {
        String stringNumber = Integer.toString(number);
        ArrayList<Character> numbers = new ArrayList<>();
        for (var i = 0; i < stringNumber.length(); i++) {
            if (numbers.contains(stringNumber.charAt(i)))
                return true;
            numbers.add(stringNumber.charAt(i));
        }
        return false;
    }
}
