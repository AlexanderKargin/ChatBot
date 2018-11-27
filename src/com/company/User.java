package com.company;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String name;
    private Integer tries;
    private List<Integer> cowsAndBullsNumber = new ArrayList<>();
    public Integer numberOfDigits;

    public User(String name){
        this.name = name;
        tries = 0;
        numberOfDigits = 0;
    }

    public void setCowsAndBullsNumber(List<Integer> mainNumber){
        cowsAndBullsNumber = mainNumber;
    }

    public String getStringCowsAndBullsNumber(){
            StringBuilder res = new StringBuilder();
            for (var numb:cowsAndBullsNumber)
                res.append(numb);
            return res.toString();
    }

    public List<Integer> getCowsAndBullsNumber(){
        return cowsAndBullsNumber;
    }

    public void increaseTries(){
        tries++;
    }

    public void setTries(Integer tries){ this.tries = tries; }

    public Integer getTries(){
        return tries;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(name);
        res.append("/");
        res.append(tries.toString());
        res.append("/\n");
        return res.toString();
    }
}
