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
        if (parts.length == 15 && Integer.parseInt(parts[parts.length - 1]) <= (user.getTries())) {
            System.out.println("You didn't set new high score :(");
            return;
        }

        ArrayList<Pair<String, Integer>> scores = new ArrayList<>();
        try (FileWriter writer = new FileWriter("src/com/company/history.txt", false)) {
            if (parts.length == 1){
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
            scores.add(new Pair<>(user.name, user.getTries()));
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
}
