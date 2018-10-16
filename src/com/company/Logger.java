package com.company;

import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Logger {
    public void checkUserLog(User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        if (new File(fileName).exists()){
            System.out.println("There is a save with the same name, do you want continue saved game? Y/N");
            Scanner input = new Scanner(System.in);
            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("Y")){
                printExistingLog(fileName);
            }
            else{
                deleteExistingLog(fileName);
                System.out.println("New game started");
            }
        }
        else{
            try{
                if (new File(fileName).createNewFile()){
                    System.out.println("New game started");
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printExistingLog(String fileName){
        System.out.println("Existing Log");
    }

    private void deleteExistingLog(String filename){
        try (FileWriter writer = new FileWriter(filename, false)){
            writer.write("");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String getUserLog(User user, Pair<Integer, Integer> cowsBulls, Integer tries){
        return String.format("%s/%s/%d/%d/%d/\n", user.name, user.cowsAndBullsNumber,
                tries, cowsBulls.getKey(), cowsBulls.getValue());
    }

    public void saveLog(User user, Pair<Integer, Integer>  cowsBulls, Integer tries){
        String log = getUserLog(user, cowsBulls, tries);
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try (FileWriter writer = new FileWriter(fileName, true)){
            writer.write(log);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
