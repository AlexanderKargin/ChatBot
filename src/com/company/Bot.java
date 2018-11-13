package com.company;

import javafx.scene.input.InputMethodTextRun;
import javafx.util.Pair;

import java.util.*;

public class Bot{
    private List<Integer> mainNumber = new ArrayList<>();
    private int numberOfDigits = 0;
    public boolean gameOver = false;
    private Saver saver = new Saver();
    private HighScoresMaker highScoresMaker = new HighScoresMaker();

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

    public String handleInput(String str, User user){
        int guess;
        if (user.getTries() > 0 && (str.equals("/q")|| str.equals("/quit"))) {
            gameOver = true;
        }
        else {
            try {
                guess = parseInput(str, user);
            } catch (WrongInputException e) {
                return e.getMessage();
            } catch (NumberFormatException e) {
                return "Incorrect Input";
            }
            return makeAnswer(user, guess);
        }
        return "";
    }

    public Integer parseInput(String str, User user) throws WrongInputException {
        // сначала обработка команд, потом ошибки, а потом если корректный ввод
        int input = Integer.parseInt(str);
        if (numberOfDigits != 0 && Integer.toString(input).length() != numberOfDigits)
            throw new WrongInputException("Wrong number of digits");
        else if (numberOfDigits != 0 && areThereRepeats(input))
            throw new WrongInputException("There are repetitions in number");
        else if (numberOfDigits == 0 && input > 3 && input < 6) {
            numberOfDigits = input;
            createNumber();
            saver.addNumber(this.toString(), user);
            user.cowsAndBullsNumber = this.toString();
        }
        else if (numberOfDigits != 0 && Integer.toString(input).length() == numberOfDigits
                && !areThereRepeats(input)) {
            user.increaseTries();
            return input;
        }
        return 0;
    }

    public String makeAnswer(User user, Integer guess){
        if (gameOver)
            return ("Game is over");
        if (numberOfDigits == 0)
            return ("Input the number of digits(4 or 5)");
        if (guess == 0)
            return ("Make your guess");
        Pair<Integer, Integer> result = checkCowsAndBulls(guess);
        if (result.getValue() == numberOfDigits) {
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