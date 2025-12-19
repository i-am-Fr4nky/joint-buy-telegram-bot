package ru.ange.jointbuy.telegram.bot.model;

import lombok.AllArgsConstructor;

public record PurchaseInlineAware (
        String thumbnailUrl,
        String title,
        String description) {}
