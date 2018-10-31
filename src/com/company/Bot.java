package com.company;

import javafx.scene.input.InputMethodTextRun;
import javafx.util.Pair;

import java.util.*;

public class Bot{
    private List<Integer> mainNumber = new ArrayList<>();
    private int numberOfDigits = 0;
    private int guess = 0;
    public boolean gameOver = false;
    private boolean hasSave = false;
    private String loadedSave = "";
    private int error = 0;
    private Map<Integer, String> errorDict = makeDict();
    private Saver saver = new Saver();
    private HighScoresMaker highScoresMaker = new HighScoresMaker();

    private Map<Integer, String> makeDict(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Wrong number of digits");
        map.put(2, "There are repetitions in number");
        map.put(3, "Incorrect input");
        return map;
    };

    public void setMainNumber(List<Integer> temp){
        mainNumber = temp;
    }

    public List<Integer> getMainNumber(){
        return mainNumber;
    }

    private void createNumber(){
        Random random = new Random();
        mainNumber.add(random.nextInt(9) + 1);
        while (mainNumber.size() < numberOfDigits){
            int newNumber = (random.nextInt(10));
            if (!mainNumber.contains(newNumber))
                mainNumber.add(newNumber);
        }
    }

    private List<Integer> parseMainNumber(Integer input){
        ArrayList<Integer> result = new ArrayList<>();
        while(input > 0){
            result.add(input % 10);
            input /= 10;
        }
        Collections.reverse(result);
        return result;
    }

    public void readInput(String str, User user) {
        error = 0;
        try {
            int input = Integer.parseInt(str);

            if (numberOfDigits != 0 && Integer.toString(input).length() != numberOfDigits)
                error = 1;
            if (numberOfDigits != 0 && areThereRepeats(input))
                error = 2;

            if (numberOfDigits == 0 && input > 3 && input < 6) {
                numberOfDigits = input;
                createNumber();
                saver.addNumber(this.toString(), user);
                user.cowsAndBullsNumber = this.toString();
            }
            if (numberOfDigits != 0 && Integer.toString(input).length() == numberOfDigits && !areThereRepeats(input)) {
                guess = input;
                user.increaseTries();
            }
        }
        catch (NumberFormatException e)
        {
            if (user.getTries() > 0 && (str.equals("/q")|| str.equals("/quit"))) {
                gameOver = true;
            }
            else if (hasSave && (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes"))){
                loadedSave = loadSave(user);
                hasSave = false;
            }
            else if (hasSave && (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no"))){
                loadedSave = "";
                saver.deleteExistingSave(user);
                hasSave = false;
            }
            else
                error = 3;
        }
    }

    public String makeAnswer(User user){
        if (gameOver) {
            return ("Game is over");
        }
        if (error != 0)
            return (errorDict.get(error));
        if (numberOfDigits == 0) {
            if (!saver.checkUserSave(user)) {
                return ("Input the number of digits(4 or 5)");
            }
            hasSave = true;
            return ("There is a save with the same name, do you want continue saved game? Y/N");
        }
        if (guess == 0) {
            if (!loadedSave.isEmpty()){
                return loadedSave;
            }
            return ("Make your guess");
        }
        Pair<Integer, Integer> result = checkCowsAndBulls(guess);
        if (result.getValue() == 4) {
            gameOver = true;
            highScoresMaker.saveHighScore(user);
            saver.deleteExistingSave(user);
            return (String.format("Congratulations! You win! \nAmount of tries %d \n" + this.toString(), user.getTries()));
        } else {
            saver.save(user, result, guess);
            return (String.format("Cows: %d, Bulls: %d.", result.getKey(), result.getValue()));
        }
    }

    public Pair<Integer, Integer> checkCowsAndBulls(int guessNumber){
        int cows = 0;
        int bulls = 0;
        ArrayList<Integer> partsOfNumber = new ArrayList<>();
        while (guessNumber > 0){
            partsOfNumber.add(guessNumber % 10);
            guessNumber /= 10;
        }
        Collections.reverse(partsOfNumber);
        for (var i = 0; i < partsOfNumber.size(); i++) {
            if (partsOfNumber.get(i).equals(mainNumber.get(i)))
                bulls++;
            else if (mainNumber.contains(partsOfNumber.get(i)))
                cows++;
        }
        return new Pair<>(cows, bulls);
    }

    public boolean areThereRepeats(int number) {
        String stringNumber = Integer.toString(number);
        ArrayList<Character> numbers = new ArrayList<>();
        for (var i = 0; i < stringNumber.length(); i++) {
            if (numbers.contains(stringNumber.charAt(i)))
                return true;
            numbers.add(stringNumber.charAt(i));
        }
        return false;
    }

    private String loadSave(User user){
        SaveInformation information = saver.parseExistingSave(user);
        mainNumber = parseMainNumber(information.getMainNumber());
        user.setTries(information.getTries());
        numberOfDigits = mainNumber.size();
        return information.getLogInfo();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var numb:mainNumber) {
            res.append(numb);
        }
        return res.toString();
    }
}