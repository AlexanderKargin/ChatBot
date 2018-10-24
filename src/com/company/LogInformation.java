package com.company;

public class LogInformation {
    private Integer mainNumber;
    private Integer tries;
    private String logInfo;

    public LogInformation(Integer number, Integer amountOfTries, String info){
        mainNumber = number;
        tries = amountOfTries;
        logInfo = info;
    }

    public Integer getMainNumber(){
        return mainNumber;
    }

    public Integer getTries(){
        return tries;
    }

    public String getLogInfo(){
        return logInfo;
    }
}
