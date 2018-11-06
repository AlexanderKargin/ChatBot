package tests;

import com.company.SaveInformation;
import com.company.Saver;
import com.company.User;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SaverTests {

    private User user = new User("testUser");
    private Saver saver = new Saver();
    private String fileName = String.format("src/com/company/logs/%s.txt", user.name);
    private File file = new File(fileName);

    @Test
    public void checkUserSaveTest(){
        try {
            file.createNewFile();
            assertFalse(saver.checkUserSave(user));
            file.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void parseExistingSaveTest(){
        try{
            file.createNewFile();
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("1234\nguess:1234 cows:0 bulls:0\n");
            writer.close();
            SaveInformation info = saver.parseExistingSave(user);
            assertEquals(1, (int)info.getTries());
            assertEquals(1234, (int)info.getMainNumber());
            assertEquals("guess:1234 cows:0 bulls:0\n", info.getLogInfo());
            file.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deleteExistingSaveTest(){
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("1234\nguess:1234 cows:0 bulls:0\n");
            writer.close();
            saver.deleteExistingSave(user);
            FileReader reader = new FileReader(fileName);
            Scanner scan = new Scanner(reader);
            assertFalse(scan.hasNextLine());
            scan.close();
            reader.close();
            file.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void saveTest(){
        try {
            file.createNewFile();
            saver.save(user, new Pair<>(2, 2), 1234);
            FileReader reader = new FileReader(fileName);
            Scanner scan = new Scanner(reader);
            String line = scan.nextLine();
            assertEquals("guess:1234 cows:2 bulls:2", line);
            scan.close();
            reader.close();
            file.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addNumberTest(){
        try {
            file.createNewFile();
            saver.addNumber("1234", user);
            FileReader reader = new FileReader(fileName);
            Scanner scan = new Scanner(reader);
            String line = scan.nextLine();
            assertEquals("1234", line);
            scan.close();
            reader.close();
            file.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
