package com.handtruth.bot.adapter.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String getCode();
    void execute(Update update);
}
