package ru.ange.jointbuy.telegram.bot.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class PurchaseImpl { // TODO rename and use for all Purchase

    private final Double cost;
    private final String name;

    private final UserImpl creator;
    private final List<UserImpl> members;
}
