package com.handtruth.bot.adapter.controllers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.handtruth.bot.adapter.utils.Bot;

@SuppressWarnings("WeakerAccess")
public class TMBot extends TelegramLongPollingBot implements Bot {
    private volatile static String USERNAME = null;
    private volatile static String TOKEN = null;

    public static volatile TMBot Instance = new TMBot();

    //    private String smile_emoji = EmojiParser.parseToUnicode("\uD83D\uDE0B");
    private volatile Controller controller = null;
    private Runnable init = null;


    private TMBot() {
        super();
        if (init != null) {
            init.run();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText() &&
                CommandController.Instance.isCommand(update)) {
            CommandController.Instance.execute(update);
        } else {
            controller.executeAsync(update);
        }
    }

    /**
     * Метод отправки сообщения в чат
     * @param text текст сообщения
     * @param chatID идентификатор чата
     */
    public synchronized void sendMsg(String text, long chatID) {
        sendMsg(text, chatID, new InlineKeyboardMarkup());
    }

    /**
     * Метод отправки сообщения в чат
     * @param text текст сообщения
     * @param chatID идентификатор чата
     * @param inlineKeyboardMarkup кнопки под сообщением
     */
    public synchronized void sendMsg(String text, long chatID, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setReplyMarkup(inlineKeyboardMarkup)
                .enableMarkdown(true)
                .setChatId(chatID)
                .setText(text);

        sendMsg(sendMessage);
    }

    /**
     * Метод отправки сообщения в чат
     * @param text текст сообщения
     * @param chatID идентификатор чата
     * @param replyKeyboardMarkup кнопки в клавиатуре чата
     */
    public synchronized void sendMsg(String text, long chatID, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setReplyMarkup(replyKeyboardMarkup)
                .enableMarkdown(true)
                .setChatId(chatID)
                .setText(text);

        sendMsg(sendMessage);
    }

    /**
     * Метод отправки сообщения в чат
     * @param message объект запроса - SendMessage (уже собранный)
     */
    public synchronized void sendMsg(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("Could not send message", TAG, e);
        }
    }

    /**
     * Метод отправки фотографии в чат
     * @param path путь к фотографии
     * @param msg сопутствующие сообщение
     * @param chat_id идентификатор чата
     */
    public synchronized void sendPhoto(String path, String msg, long chat_id) {
        sendPhoto(path, msg, chat_id, new InlineKeyboardMarkup());
    }

    /**
     * Метод отправки фотографии в чат
     * @param path путь к фотографии
     * @param msg сопутствующие сообщение
     * @param chat_id идентификатор чата
     * @param inlineKeyboardMarkup кнопки под сообщением
     */
    public synchronized void sendPhoto(String path, String msg, long chat_id, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendPhoto sendPhoto = new SendPhoto();
        java.io.File file = new java.io.File(path);

        sendPhoto.setReplyMarkup(inlineKeyboardMarkup)
                .setChatId(chat_id)
                .setPhoto(file)
                .setCaption(msg);

        sendPhoto(sendPhoto);

    }

    /**
     * Метод отправки фотографии в чат
     * @param photo объект запроса - SendPhoto (уже собранный)
     */
    public synchronized void sendPhoto(SendPhoto photo) {
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param msg сообщение, которое необходимо переслать
     * @param chat_id идентификатор чата
     */
    public synchronized void forwardMsg(Message msg, long chat_id) {
        ForwardMessage forwardMessage = new ForwardMessage(chat_id, msg.getChatId(), msg.getMessageId());
        try {
            execute(forwardMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод загрузки обычного файла
     * @param id идентификатор файла
     * @return файл на жестком диске
     */
    public synchronized java.io.File downloadFileIO(String id) {
        try {
            File file = download(id);
            return downloadFile(file);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Метод загрузки telegram файла
     * @param id идентификатор файла
     * @return telegram file
     * @throws TelegramApiException ошибка при использовании
     */
    public synchronized File download(String id) throws TelegramApiException {
        return execute(new GetFile().setFileId(id));
    }

    @Override
    public synchronized String getBotUsername() {
        return USERNAME;
    }

    @Override
    public synchronized void setUsername(String username) {
        USERNAME = username;
    }

    @Override
    public synchronized String getBotToken() {
        return TOKEN;
    }

    @Override
    public synchronized void setToken(String token) {
        TOKEN = token;
    }

    @Override
    public synchronized void setController(Controller controller) {
        this.controller = controller;
    }

    public synchronized void setInit(Runnable runnable) {
        this.init = runnable;
    }
}
