package com.handtruth.bot.adapter.controllers;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Controller {
    public Controller() {
    }

    private TMBot bot = TMBot.Instance;

    public abstract void execute(Update update);

    final void executeAsync(Update update) {
        Thread thread = new Thread(() -> execute(update));
    }
}
