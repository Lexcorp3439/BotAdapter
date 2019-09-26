package com.handtruth.bot.adapter.utils;

import com.handtruth.bot.adapter.controllers.Controller;

public interface Bot {
    String TAG = "Bot";
    void setUsername(String username);

    void setToken(String token);

    void setController(Controller controller);
}
