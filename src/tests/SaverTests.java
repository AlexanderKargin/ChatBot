package tests;

import com.company.Bot;
import com.company.SaveInformation;
import com.company.Saver;
import com.company.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SaverTests {
    @Test
    public void checkUserTest(){
        User user = new User("testUser");
        Saver saver = new Saver();
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        File file = new File(fileName);
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
        User user = new User("testUser");
        Saver saver = new Saver();
        String fileName = String.format("src/com/company/logs/%s.txt", user.name);
        File file = new File(fileName);
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
}
