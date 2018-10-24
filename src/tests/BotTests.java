package tests;

import com.company.Bot;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


public class BotTests {
    @Test
    public void testFullBulls() {
        Bot bot = new Bot();
        bot.setMainNumber(Arrays.asList(1, 2, 3, 4));
        assertEquals(4, (int)bot.checkCowsAndBulls(1234).getValue());
    }

    @Test
    public void testFullCows(){
        Bot bot = new Bot();
        bot.setMainNumber(new ArrayList<Integer>(Arrays.<Integer> asList(1, 2, 3, 4)));
        assertEquals(4, (int)bot.checkCowsAndBulls(4321).getKey());
    }

    @Test
    public void testCowsAndBulls(){
        Bot bot = new Bot();
        bot.setMainNumber(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
        assertEquals(2, (int)bot.checkCowsAndBulls(1243).getValue());
        assertEquals(2, (int)bot.checkCowsAndBulls(1243).getKey());
    }

    @Test
    public void testChatRepeatsTrue(){
        Bot bot = new Bot();
        assertTrue(bot.areThereRepeats(1233));
    }

    @Test
    public void testChatRepeatsFalse(){
        Bot bot = new Bot();
        assertFalse(bot.areThereRepeats(1234));
    }
}
