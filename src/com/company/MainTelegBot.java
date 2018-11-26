package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MainTelegBot{
    private static String PROXY_HOST = System.getenv("PROXY_HOST");
    private static Integer PROXY_PORT = Integer.parseInt(System.getenv("PROXY_PORT"));
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

            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            TelegBot myBot = new TelegBot("CowsAndBulls", "574223491:AAE0KkuaDSiXPNvlr9SudceuulT1O93K1uk", botOptions);

            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}