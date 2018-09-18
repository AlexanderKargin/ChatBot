package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Bot {
    ArrayList<Integer> mainNumber = new ArrayList<>();

    public Bot(int amountOfNumbers){
        mainNumber.add((int)(Math.random() * 9 + 1));
        while (mainNumber.size() < amountOfNumbers){
            int newNumber = (int)(Math.random() * 10);
            if (!mainNumber.contains(newNumber))
                mainNumber.add(newNumber);
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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var numb:mainNumber) {
            res.append(numb);
        }
        return res.toString();
    }
}