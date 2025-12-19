package ru.ange.jointbuy.telegram.bot.model;

import lombok.Builder;

@Builder
public record PurchaseInlineDraft(
        Double cost,
        String name,
        String userName,
        String firstName,
        String lastName
) {}
