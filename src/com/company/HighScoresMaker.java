package com.company;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class HighScoresMaker {
    private String fileName = "src/com/company/HighScore.txt";
    public void saveHighScore(User user) {
        String fileName = "src/com/company/HighScore.txt";
        String[] parts = getExistingHighScores(fileName);
        if (parts.length == 15 && Integer.parseInt(parts[parts.length - 1]) <= (user.getTries())) {
            System.out.println("You didn't set new high score :(");
            return;
        }
        try (FileWriter writer = new FileWriter(fileName, false)) {
            if (parts.length == 1){
                writer.write("1/" + user.toString());
                return;
            }
            ArrayList<Pair<String, Integer>> scores = getNewHighScores(parts, user);
            for (var i = 0; i < scores.size(); i++){
                writer.write(String.format("%d/%s/%d/\n", i + 1, scores.get(i).getKey(), scores.get(i).getValue()));
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String[] getExistingHighScores(String fileName){
        StringBuilder text = new StringBuilder();
        try (FileReader reader = new FileReader(fileName)){
            Scanner scan = new Scanner(reader);
            while(scan.hasNextLine()){
                text.append(scan.nextLine());
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return text.toString().split("/");
    }

    public ArrayList<Pair<String, Integer>> getNewHighScores(String[] parts, User user){
        ArrayList<Pair<String, Integer>> scores = new ArrayList<>();
        for (var i = 0; i < parts.length / 3; i++){
            scores.add(new Pair<>(parts[i * 3 + 1], Integer.parseInt(parts[i * 3 + 2])));
        }
        if (scores.size() == 5){
            scores.remove(scores.get(4));
        }
        scores.add(new Pair<>(user.name, user.getTries()));
        Collections.sort(scores, Comparator.comparing(p -> p.getValue()));
        return scores;
    }
}
