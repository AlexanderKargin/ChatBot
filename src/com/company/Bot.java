package com.company;

import javafx.scene.input.InputMethodTextRun;
import javafx.util.Pair;

import java.util.*;

public class Bot{
    private List<Integer> mainNumber = new ArrayList<>();
    private int numberOfDigits = 0;
    private int guess = 0;
    public boolean gameOver = false;
    private int error = 0;
    private Map<Integer, String> errorDict = makeDict();
    private Logger logger = new Logger();
    private Saver saver = new Saver();

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
                logger.addNumber(this.toString(), user);
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
            else
                error = 3;
        }
    }

    public String makeAnswer(User user){
        if (!gameOver) {
            if (error != 0)
                return (errorDict.get(error));
            if (numberOfDigits == 0) {
                if (logger.checkUserLog(user)){
                    Pair<Pair<Integer, Integer>, String> information = logger.parseExistingLog(user);
                    mainNumber = parseMainNumber(information.getKey().getKey());
                    user.setTries(information.getKey().getValue());
                    numberOfDigits = mainNumber.size();
                    return information.getValue();
                }
                else {
                    return ("Input the number of digits(4 or 5)");
                }
            }
            if (guess == 0)
                return ("Make your guess");
            Pair<Integer, Integer> result = checkCowsAndBulls(guess);
            if (result.getValue() == 4) {
                gameOver = true;
                saver.saveHighScore(user);
                logger.deleteExistingLog(user);
                return (String.format("Congratulations! You win! \nAmount of tries %d \n" + this.toString(), user.getTries()));
            } else {
                logger.saveLog(user, result, guess);
                return (String.format("Cows: %d, Bulls: %d.", result.getKey(), result.getValue()));
            }
        }
        else {
            return ("Game is over");
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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var numb:mainNumber) {
            res.append(numb);
        }
        return res.toString();
    }
}