package tests;

import com.company.HighScoresMaker;
import com.company.User;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class HighScoresMakerTests {

    private User user = new User("testUser");
    private String fileName = "src/com/company/HighScoreTest.txt";
    private File file = new File(fileName);
    private HighScoresMaker maker = new HighScoresMaker();

    @Test
    void getExistingHighScoresTest(){
        try{
            file.createNewFile();
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("1/doc/3/\n");
            writer.close();
            String [] scores = maker.getExistingHighScores(fileName);
            assertEquals("1", scores[0]);
            assertEquals("doc", scores[1]);
            assertEquals("3", scores[2]);
            file.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getNewHighScoresTest(){
        user.setTries(2);
        String [] parts = {"1", "doc", "3"};
        ArrayList<Pair<String, Integer>> result = maker.getNewHighScores(parts, user);
        assertEquals("testUser", result.get(0).getKey());
        assertEquals(2, (int)result.get(0).getValue());
        assertEquals("doc", result.get(1).getKey());
        assertEquals(3, (int)result.get(1).getValue());
    }
}
