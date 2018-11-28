import javafx.util.Pair;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-t"))
            MainTelegBot.main();
        else {
            Chat chat = new Chat();
            //chat.Run();
        }
    }
}

