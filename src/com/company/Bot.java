package com.company;

import javafx.scene.input.InputMethodTextRun;
import javafx.util.Pair;

import java.util.*;

public class Bot{
    private List<Integer> mainNumber = new ArrayList<>();
    private int numberOfDigits = 0;
    private int guess = 0;
    private int tries = 0;
    public boolean gameOver = false;
    private int error = 0;
    private Map<Integer, String> errorDict = makeDict();

    private Map<Integer, String> makeDict(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Wrong number of digits");
        map.put(2, "There are repetitions in number");
        map.put(3, "Incorrect input");
        return map;
    };

    public void setMainNumber(List<Integer> temp){
        mainNumber = temp;
    }

    public List<Integer> getMainNumber(){
        return mainNumber;
    }

    private void createNumber(){
        Random random = new Random();
        mainNumber.add(random.nextInt(9) + 1);
        while (mainNumber.size() < numberOfDigits){
            int newNumber = (random.nextInt(10));
            if (!mainNumber.contains(newNumber))
                mainNumber.add(newNumber);
        }
    }

    public void readInput(String str, User user) {
        error = 0;
        try {
            int input = Integer.parseInt(str);

            if (numberOfDigits != 0 && Integer.toString(input).length() != numberOfDigits)
                error = 1;
            if (numberOfDigits != 0 && areThereRepeats(input))
                error = 2;

            if (numberOfDigits == 0 && input > 3 && input < 6) {
                numberOfDigits = input;
                createNumber();
                user.cowsAndBullsNumber = this.toString();
                System.out.println(this.toString());
            }
            if (numberOfDigits != 0 && Integer.toString(input).length() == numberOfDigits && !areThereRepeats(input)) {
                guess = input;
                tries++;
                user.tries = Integer.toString(tries);
            }
        }
        catch (NumberFormatException e)
        {
            error = 3;
        }
    }

    public String makeAnswer(){
        if (error != 0)
            return (errorDict.get(error));
        if (numberOfDigits == 0)
            return ("Input the number of digits(4 or 5)");
        if (guess == 0)
            return ("Make your guess");
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