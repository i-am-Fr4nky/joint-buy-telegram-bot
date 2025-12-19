package ru.ange.jointbuy.telegram.bot.model.msg;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class PurchaseMsg {

    public String text;
    public List<List<InlineKeyboardButton>> keys;

    public String getText() {
        return "";
    }

    public List<List<InlineKeyboardButton>> getKeys() {
        return null;
    }

}
