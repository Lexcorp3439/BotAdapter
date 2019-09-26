package com.handtruth.bot.adapter;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.handtruth.bot.adapter.controllers.CommandController;
import com.handtruth.bot.adapter.controllers.Controller;
import com.handtruth.bot.adapter.utils.Bot;
import com.handtruth.bot.adapter.utils.Command;
import com.handtruth.bot.adapter.controllers.TMBot;

public class BotRunner {
    private Bot bot = TMBot.Instance;
    private String username;
    private String token;
    private Controller controller;
    private static final String TAG = "BOT_RUNNER";


    public BotRunner(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public BotRunner() {
    }

    public BotRunner authorization(String username, String token) {
        this.username = username;
        this.token = token;
        return this;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public BotRunner registerCommand(Command command) {
        CommandController.Instance.register(command);
        return this;
    }

    public void run() {
        if (controller == null) {
            throw new IllegalStateException("Controller must be initialisation");
        }
        if (username == null || token == null) {
            throw new IllegalStateException("Bot`s username and token must be initialisation");
        }
        bot.setUsername(username);
        bot.setToken(token);
        bot.setController(controller);

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot((TMBot) bot);
            BotLogger.debug(TAG, "SUCCESS");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static final String SET_KEY = "proxySet";
    private static final String HOST_KEY = "socksProxyHost";
    private static final String PORT_KEY = "socksProxyPort";
    private static final String SET_VALUE = "true";

    public BotRunner setProxy(String address, String port) {
        System.getProperties().put(SET_KEY, SET_VALUE);
        System.getProperties().put(HOST_KEY, address);
        System.getProperties().put(PORT_KEY, port);
        BotLogger.debug(TAG, "Proxy connect");
        return this;
    }

    public BotRunner removeProxy() {
        System.getProperties().remove(SET_KEY);
        System.getProperties().remove(HOST_KEY);
        System.getProperties().remove(PORT_KEY);
        BotLogger.debug(TAG, "Proxy remove");
        return this;
    }
}
