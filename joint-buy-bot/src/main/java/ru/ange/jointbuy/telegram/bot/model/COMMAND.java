package ru.ange.jointbuy.telegram.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum COMMAND {

    LIST ("show_purchases"); // TODO "/show_purchases@JointBuyBot" -> "/list"

    @Getter
    private final String command;
}
