package com.company;

import javafx.util.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Logger {
    public Boolean checkUserLog(User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        File file = new File(fileName);
        if (file.exists()){
            if (file.length() > 0) {
                System.out.println("There is a save with the same name, do you want continue saved game? Y/N");
                Scanner input = new Scanner(System.in);
                String answer = input.nextLine();
                if (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("Y")) {
                    return true;
                } else {
                    deleteExistingLog(fileName);
                    System.out.println("New game started");
                    return false;
                }
            }
        }
        else{
            try{
                new File(fileName).createNewFile();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public LogInformation parseExistingLog(User user){
        // main number
        // number/cows/bulls/
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        StringBuilder logs = new StringBuilder();
        try (FileReader reader = new FileReader(fileName)){
            Scanner scan = new Scanner(reader);
            int mainNumber = Integer.parseInt(scan.nextLine());
            int tries = 0;
            while(scan.hasNextLine()){
                logs.append(scan.nextLine());
                logs.append("\n");
                tries++;
            }
            return new LogInformation(mainNumber, tries, logs.toString());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return new LogInformation(0, 0, "");
    }

    private void deleteExistingLog(String fileName){
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write("");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteExistingLog(User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write("");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String makeUserLog(Pair<Integer, Integer> cowsBulls, Integer guess){
        return String.format("guess:%d cows:%d bulls:%d\n", guess, cowsBulls.getKey(), cowsBulls.getValue());
    }


    public void saveLog(User user, Pair<Integer, Integer>  cowsBulls, Integer guess){
        String log = makeUserLog(cowsBulls, guess);
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try (FileWriter writer = new FileWriter(fileName, true)){
            writer.write(log);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void addNumber(String number, User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try (FileWriter writer = new FileWriter(fileName, true)){
            writer.write(number + "\n");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
