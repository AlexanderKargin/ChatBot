package com.company;

import java.util.ArrayList;

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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var numb:mainNumber) {
            res.append(numb);
        }
        return res.toString();
    }
}