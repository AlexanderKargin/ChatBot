package tests;

import com.company.LogInformation;
import com.company.Logger;
import com.company.User;
import com.company.Bot;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTests {
    @Test
    public void testParserExistingLog() {
        Logger logger = new Logger();
        User user = new User("testUser");
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try {
            boolean testFile = new File(fileName).createNewFile();
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("1234\nguess:2345 cows:1 bulls:2\n");
            writer.close();
            LogInformation info = logger.parseExistingLog(user);
            assertEquals(1234, (int) info.getMainNumber());
            assertEquals(1, (int) info.getTries());
            assertTrue("guess:2345 cows:1 bulls:2\n".equalsIgnoreCase(info.getLogInfo()));
            boolean deleting = new File(fileName).delete();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeleteLog() {
        User user = new User("testUser");
        Logger logger = new Logger();
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try {
            boolean creating = new File(fileName).createNewFile();
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("1234\nguess:2345 cows:1 bulls:2\n");
            writer.close();
            logger.deleteExistingLog(user);
            File testFile = new File(fileName);
            assertEquals(0, testFile.length());
            boolean deleting = new File(fileName).delete();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSaveUserLog(){
        Logger logger = new Logger();
        User user = new User("testUser");
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try{
            boolean creating = new File(fileName).createNewFile();
            logger.saveLog(user, new Pair<>(1, 0), 1234);
            FileReader reader = new FileReader(fileName);
            Scanner scanner = new Scanner(reader);
            assertTrue("guess:1234 cows:1 bulls:0".equalsIgnoreCase(scanner.nextLine()));
            reader.close();
            boolean deleting = new File(fileName).delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAddNumber(){
        Logger logger = new Logger();
        User user = new User("testUser");
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        try{
            boolean creating = new File(fileName).createNewFile();
            logger.addNumber("1234", user);
            FileReader reader = new FileReader(fileName);
            Scanner scanner = new Scanner(reader);
            assertTrue("1234".equalsIgnoreCase(scanner.nextLine()));
            reader.close();
            boolean deleting = new File(fileName).delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
