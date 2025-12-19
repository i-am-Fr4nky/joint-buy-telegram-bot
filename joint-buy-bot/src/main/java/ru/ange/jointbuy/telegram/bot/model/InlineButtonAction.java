package ru.ange.jointbuy.telegram.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InlineButtonAction {
    JOIN("inline.answer.purchase.btt.join"),
    EDIT("inline.answer.purchase.btt.edit"),
    DELETE("inline.answer.purchase.btt.delete"),
    RESTORE("inline.answer.purchase.btt.restore");

    @Getter
    private final String msgCode;
}
