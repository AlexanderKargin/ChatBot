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

    private void createNumber(){
        Random random = new Random();
        mainNumber.add(random.nextInt(9) + 1);
        while (mainNumber.size() < numberOfDigits){
            int newNumber = (random.nextInt(10));
            if (!mainNumber.contains(newNumber))
                mainNumber.add(newNumber);
        }
    }

    public List<Integer> parseMainNumber(Integer input){
        ArrayList<Integer> result = new ArrayList<>();
        while(input > 0){
            result.add(input % 10);
            input /= 10;
        }
        Collections.reverse(result);
        return result;
    }
    // Вынести инициализацию +
    // Сделать один метод с обработкой вода
    // пробрасывать ошибку из ReadInput с текстом ошибки
    public void readInput(String str, User user) {
        // сначала обработка команд, потом ошибки, а потом если корректный ввод
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
            else {
                error = 3;
            }
        }
    }

    public String makeAnswer(User user){
        if (gameOver)
            return ("Game is over");
        if (error != 0)
            return (errorDict.get(error));
        if (numberOfDigits == 0)
            return ("Input the number of digits(4 or 5)");
        if (guess == 0)
            return ("Make your guess");
        Pair<Integer, Integer> result = checkCowsAndBulls(guess);
        if (result.getValue() == 4) {
            gameOver = true;
            highScoresMaker.saveHighScore(user);
            saver.deleteExistingSave(user);
            return (String.format("Congratulations! You win! \nAmount of tries %d \n" + this.toString(),
                    user.getTries()));
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

    public void loadSave(SaveInformation information){
        mainNumber = parseMainNumber(information.getMainNumber());
        numberOfDigits = mainNumber.size();
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