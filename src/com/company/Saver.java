package com.company;

import javafx.util.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Saver {
    public Boolean checkUserSave(User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        File file = new File(fileName);
        if (!file.exists()) {
            createSaveFile(user);
            return false;
        }
        if (file.length() <= 0) {
            return false;
        }
        return true;
    }

    private void createSaveFile(User user) {
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        File file = new File(fileName);
        try {
            file.createNewFile();
        }
        catch(IOException e){
                System.out.println(e.getMessage());
        }
    }

    public SaveInformation parseExistingSave(User user){
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
            return new SaveInformation(mainNumber, tries, logs.toString());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return new SaveInformation(0, 0, "");
    }

    private void deleteExistingSave(String fileName){
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write("");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteExistingSave(User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write("");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String makeUserSaveInfo(Pair<Integer, Integer> cowsBulls, Integer guess){
        return String.format("guess:%d cows:%d bulls:%d\n", guess, cowsBulls.getKey(), cowsBulls.getValue());
    }


    public void save(User user, Pair<Integer, Integer>  cowsBulls, Integer guess){
        String log = makeUserSaveInfo(cowsBulls, guess);
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
