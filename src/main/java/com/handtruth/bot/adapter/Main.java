package com.handtruth.bot.adapter;

public class Main {
    private static final String PROXY_PORT = "34332";
    private static final String PROXY_ADDRESS = "109.196.82.214";

    public static void main(String[] args) {
        BotRunner runner = new BotRunner();
        runner
                .authorization("java_school_bot", "897043581:AAH_ALULIfjBi1TxIMI6tPlljVpIqm3xlYg")
                .setProxy(PROXY_ADDRESS, PROXY_PORT)
                .setController(new BotController())
//                .registerCommand(new HelpCommand())
                .run();
    }
}
