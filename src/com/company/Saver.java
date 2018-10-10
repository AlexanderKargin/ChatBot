package com.company;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Saver {
    public void saveHighScore(User user) {
        StringBuilder text = new StringBuilder();
        try (FileReader reader = new FileReader("src/com/company/history.txt")){
            Scanner scan = new Scanner(reader);
            while(scan.hasNextLine()){
                text.append(scan.nextLine());
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        String[] parts = text.toString().split("/");
        if (parts.length == 15 && Integer.parseInt(parts[parts.length - 1]) <= Integer.parseInt(user.tries)) {
            System.out.println("You didn't set new high score :(");
            return;
        }

        ArrayList<Pair<String, Integer>> scores = new ArrayList<>();
        try (FileWriter writer = new FileWriter("src/com/company/history.txt", false)) {
            if (parts.length == 0){
                writer.write("1/" + user.toString());
                return;
            }
            else {
                for (var i = 0; i < parts.length / 3; i++){
                    scores.add(new Pair<>(parts[i * 3 + 1], Integer.parseInt(parts[i * 3 + 2])));
                }
            }
            if (scores.size() == 5){
                scores.remove(scores.get(4));
            }
            scores.add(new Pair<>(user.name, Integer.parseInt(user.tries)));
            Collections.sort(scores, Comparator.comparing(p -> p.getValue()));

            for (var i = 0; i < scores.size(); i++){
                writer.write(String.format("%d/%s/%d/\n", i + 1, scores.get(i).getKey(), scores.get(i).getValue()));
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        try (FileReader reader = new FileReader("src/com/company/history.txt")){
//            Scanner scan = new Scanner(reader);
//            while(scan.hasNextLine()){
//                System.out.println(scan.nextLine());
//            }
//        }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
    }

    public void checkUserLog(User user){
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        if (new File(fileName).exists()){
            System.out.println("You have saved game, do you want continue? Y/N");
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
            writer.write("name|number|trie|cows|bulls\n");
            writer.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String getUserLog(User user, Pair<Integer, Integer>  cowsBulls, Integer trie){
        return String.format("%s|%s|%d|%d|%d", user.name, user.cowsAndBullsNumber,
                trie, cowsBulls.getKey(), cowsBulls.getValue());
    }

    public void saveLog(User user, Pair<Integer, Integer>  cowsBulls, Integer trie){
        String log = getUserLog(user, cowsBulls, trie);
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try (FileWriter writer = new FileWriter(fileName, true)){
            writer.write(log);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
