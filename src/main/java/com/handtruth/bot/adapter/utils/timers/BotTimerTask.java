package com.handtruth.bot.adapter.utils.timers;

import java.util.TimerTask;

import com.handtruth.bot.adapter.controllers.TMBot;

public abstract class BotTimerTask extends TimerTask {
    protected TMBot bot = TMBot.Instance;
}
