package com.company;

public class User {
    public String name;
    public String cowsAndBullsNumber;
    public String tries;

    public User(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(name);
        res.append("|");
        res.append(tries);
        res.append("|\n");
        return res.toString();
    }
}
