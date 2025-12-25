package ru.ange.jointbuy.telegram.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PurchaseInlineDraft implements Purchase {

    private final Double cost;
    private final String name;


    private final String userName;
    private final String firstName;
    private final String lastName;
}
