public interface BotInterface {
    Integer parseInput(String str, User user) throws WrongInputException;
    String makeAnswer(User user, Integer guess) throws WrongInputException;
    String handleInput(String message, User user);
}
