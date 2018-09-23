package com.company;

import javafx.scene.input.InputMethodTextRun;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bot {
    ArrayList<Integer> mainNumber = new ArrayList<>();
    private int numberOfDigits = 0;
    private int guess = 0;
    private int tries = 0;
    public boolean gameOver = false;

    private void createNumber(){
        Random random = new Random();
        mainNumber.add(random.nextInt(9) + 1);
        while (mainNumber.size() < numberOfDigits){
            int newNumber = (random.nextInt(10));
            if (!mainNumber.contains(newNumber))
                mainNumber.add(newNumber);
        }
    }

    public void readInput(Integer input) {
        if (numberOfDigits == 0 && input > 3 && input < 6) {
            numberOfDigits = input;
            createNumber();
        }
        if (numberOfDigits != 0 && Integer.toString(input).length() == numberOfDigits && !areThereRepeats(input)) {
            guess = input;
            tries++;
        }
        if (numberOfDigits != 0 && Integer.toString(input).length() != numberOfDigits && input > 1000)
            guess = -1;
        if (numberOfDigits != 0 && areThereRepeats(input) && input > 1000)
            guess = -2;
    }

    public String makeAnswer(){
        if (numberOfDigits == 0)
            return ("Input the number of digits(4 or 5)");
        if (guess == 0)
            return ("Make your guess");
        if (guess == -1)
            return("Wrong number of digits in input number");
        if (guess == -2)
            return ("There are repetitions in input number");
        Pair<Integer, Integer> result = checkCowsAndBulls(guess);
        if (result.getValue() == 4) {
            gameOver = true;
            return (String.format("Congratulations! You win! \nAmount of tries %d \n" + this.toString(), tries));
        }
        else {
            return (String.format("Cows: %d, Bulls: %d.", result.getKey(), result.getValue()));
        }
    }

    public Pair<Integer, Integer> checkCowsAndBulls(int guessNumber){
        int cows = 0;
        int bulls = 0;
        ArrayList<Integer> partsOfNumber = new ArrayList<>();
        while (guessNumber > 0){
            partsOfNumber.add(guessNumber % 10);
            guessNumber /= 10;
        }
        Collections.reverse(partsOfNumber);
        for (var i = 0; i < partsOfNumber.size(); i++) {
            if (partsOfNumber.get(i) == mainNumber.get(i))
                bulls++;
            else if (mainNumber.contains(partsOfNumber.get(i)))
                cows++;
        }
        return new Pair<>(cows, bulls);
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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var numb:mainNumber) {
            res.append(numb);
        }
        return res.toString();
    }
}