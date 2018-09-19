package com.company;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class tests {
    @Test
    public void testFullBulls() {
        Bot test = new Bot(4);
        test.mainNumber = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
        assertEquals(4, (int)test.checkCowsAndBulls(1234).getValue());
    }

    @Test
    public void testFullCows(){
        Bot test = new Bot(4);
        test.mainNumber = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
        assertEquals(4, (int)test.checkCowsAndBulls(4321).getKey());
    }

    @Test
    public void testCowsAndBulls(){
        Bot test = new Bot(4);
        test.mainNumber = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
        assertEquals(2, (int)test.checkCowsAndBulls(1243).getValue());
        assertEquals(2, (int)test.checkCowsAndBulls(1243).getKey());
    }

    @Test
    public void testChatRepeatsTrue(){
        Chat test = new Chat();
        assertEquals(true, test.areThereRepeats(1233));
    }

    @Test
    public void testChatRepeatsFalse(){
        Chat test = new Chat();
        assertEquals(false, test.areThereRepeats(1234));
    }
}
