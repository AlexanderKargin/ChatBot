package tests;

import com.company.Bot;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


public class BotTests {

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
