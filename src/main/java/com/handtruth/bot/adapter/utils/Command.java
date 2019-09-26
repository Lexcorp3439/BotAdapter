package com.handtruth.bot.adapter.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.handtruth.bot.adapter.controllers.TMBot;

public interface Command {
    TMBot bot = TMBot.Instance;

    String getCode();
    void execute(Update update);
}
