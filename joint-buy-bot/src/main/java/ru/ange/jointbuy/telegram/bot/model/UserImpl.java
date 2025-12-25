package ru.ange.jointbuy.telegram.bot.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserImpl { // TODO rename and use for all Purchase

    private final String userName;
    private final String firstName;
    private final String lastName;
}
