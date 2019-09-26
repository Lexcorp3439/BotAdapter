package com.handtruth.bot.adapter;

public class Main {
    private static final String PROXY_PORT = "1080";
    private static final String PROXY_ADDRESS = "178.197.248.213";

    public static void main(String[] args) {
        BotRunner runner = new BotRunner("", "");
        runner
                .authorization("", "")
                .setProxy(PROXY_ADDRESS, PROXY_PORT)
//                .setController(Your Controller)
//                .registerCommand(Your custom Command)
                .run();
    }
}
