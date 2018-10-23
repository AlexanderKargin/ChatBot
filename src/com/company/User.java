package com.company;

public class User {
    public String name;
    public String cowsAndBullsNumber;
    private Integer tries;

    public User(String name){
        this.name = name;
        this.tries = 0;
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
