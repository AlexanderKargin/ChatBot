package com.company;

import javafx.scene.input.InputMethodTextRun;
import javafx.util.Pair;

import java.util.*;

public class Bot{
    private Saver saver = new Saver();
    private HighScoresMaker highScoresMaker = new HighScoresMaker();

    private ArrayList<Integer> createNumber(int numberOfDigits){
        ArrayList<Integer> res = new ArrayList<>();
        Random random = new Random();
        res.add(random.nextInt(9) + 1);
        while (res.size() < numberOfDigits){
            int newNumber = (random.nextInt(10));
            if (!res.contains(newNumber))
                res.add(newNumber);
        }
        return res;
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
            return "";
        }
        try {
            guess = parseInput(str, user);
        } catch (WrongInputException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Incorrect Input";
        }
        return makeAnswer(user, guess);
    }

    public Integer parseInput(String str, User user) throws WrongInputException {
        int input = Integer.parseInt(str);
        if (user.numberOfDigits != 0 && Integer.toString(input).length() != user.numberOfDigits)
            throw new WrongInputException("Wrong number of digits");
        else if (user.numberOfDigits != 0 && areThereRepeats(input))
            throw new WrongInputException("There are repetitions in number");
        else if (user.numberOfDigits != 0 && Integer.toString(input).length() == user.numberOfDigits
                && !areThereRepeats(input)) {
            user.increaseTries();
            return input;
        }
        else if (user.numberOfDigits == 0 && input > 3 && input < 6) {
            user.numberOfDigits = input;
            user.setCowsAndBullsNumber(createNumber(user.numberOfDigits));
            saver.addNumber(this.toString(), user);
        }
        return 0;
    }

    public String makeAnswer(User user, Integer guess){
        if (user.numberOfDigits == 0)
            return ("Input the number of digits(4 or 5)");
        if (guess == 0)
            return ("Make your guess");
        Pair<Integer, Integer> result = checkCowsAndBulls(guess, user);
        if (result.getValue() == user.numberOfDigits) {
            highScoresMaker.saveHighScore(user);
            saver.deleteExistingSave(user);
            return (String.format("Congratulations! You win! \nAmount of tries %d \n" + user.getStringCowsAndBullsNumber(),
                    user.getTries()));
        } else {
            saver.save(user, result, guess);
            return (String.format("Cows: %d, Bulls: %d.", result.getKey(), result.getValue()));
        }
    }

    public Pair<Integer, Integer> checkCowsAndBulls(int guessNumber, User user){
        int cows = 0;
        int bulls = 0;
        ArrayList<Integer> partsOfNumber = new ArrayList<>();
        while (guessNumber > 0){
            partsOfNumber.add(guessNumber % 10);
            guessNumber /= 10;
        }
        Collections.reverse(partsOfNumber);
        for (var i = 0; i < partsOfNumber.size(); i++) {
            if (partsOfNumber.get(i).equals(user.getCowsAndBullsNumber().get(i)))
                bulls++;
            else if (user.getCowsAndBullsNumber().contains(partsOfNumber.get(i)))
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

    public void loadSave(SaveInformation information, User user){
        user.setCowsAndBullsNumber(parseMainNumber(information.getMainNumber()));
        user.numberOfDigits = user.getCowsAndBullsNumber().size();
    }
}