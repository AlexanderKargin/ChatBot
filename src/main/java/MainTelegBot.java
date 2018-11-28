import com.google.inject.Guice;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MainTelegBot{
    private static  String PROXY_USER = System.getenv("PROXY_USER");
    private static String PROXY_PASS = System.getenv("PROXY_PASS");

    public static void main() {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();

            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(PROXY_USER, PROXY_PASS.toCharArray());
                }
            });

            var injector = Guice.createInjector(new BasicModule());
            TelegBot myBot = injector.getInstance(TelegBot.class);

            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}