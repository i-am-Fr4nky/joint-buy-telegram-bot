package ru.ange.jointbuy.telegram.bot.model;

public interface Purchase {

    Double getCost();
    String getName();
    String getUserName();
    String getFirstName();
    String getLastName();
}
