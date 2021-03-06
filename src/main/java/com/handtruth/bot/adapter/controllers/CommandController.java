package com.handtruth.bot.adapter.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.handtruth.bot.adapter.utils.Command;

public enum  CommandController {
    Instance;

    private Map<String, Command> commands = new ConcurrentHashMap<>();

    public boolean isCommand(String code) {
        return commands.containsKey(code);
    }

    public boolean isCommand(Update update) {
        String code  = update.getMessage().getText();
        return isCommand(code.split(" ")[0]);
    }

    public void execute(Update update) {
        Thread thread = new Thread(() -> {
            commands.get(update.getMessage().getText()).execute(update);
        });
        thread.start();
    }

    public void register(Command command) {
        if (commands.containsKey(command.getCode())) {
            throw new IllegalArgumentException("Code must be uniq");
        }
        if (command.getCode().charAt(0) != '/') {
            throw new IllegalArgumentException("Code must start with '/'");
        }
        commands.put(command.getCode(), command);
    }
}
