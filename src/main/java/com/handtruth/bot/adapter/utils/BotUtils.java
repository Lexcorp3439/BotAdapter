package com.handtruth.bot.adapter.utils;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.vdurmont.emoji.EmojiParser;

public class BotUtils {
    private final static String TAG = "BOT_UTILS";
    public static String smile_with_tongue_emoji = EmojiParser.parseToUnicode("\uD83D\uDE0B");
    public static String thumbs_up_sign = EmojiParser.parseToUnicode("U+1F44D");
    public static String thumbs_down_sign = EmojiParser.parseToUnicode("U+1F44E");



    public static void setKeys(InlineKeyboardMarkup inlineKeyboardMarkup, List<String> list) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        for (String button : list) {
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(button).setCallbackData(button));
        }
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
    }

    public static void dropKeys(InlineKeyboardMarkup inlineKeyboardMarkup) {
        inlineKeyboardMarkup = new InlineKeyboardMarkup();
    }

    public static void downloadImg(String imgId, String path, java.io.File file) {
        java.io.File f = new java.io.File(path);

        if (file.renameTo(f)) {
            BotLogger.debug(TAG, "Файл успешно перемещён!");
        } else {
            if (file.delete()) {
                BotLogger.debug(TAG, "Файл был успешно удален");
            } else {
                BotLogger.debug(TAG, "Файл НЕ УДАЛЕН");
            }
        }
    }
}
