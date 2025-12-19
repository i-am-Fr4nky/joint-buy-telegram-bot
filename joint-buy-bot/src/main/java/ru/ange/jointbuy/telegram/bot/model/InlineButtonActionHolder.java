package ru.ange.jointbuy.telegram.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Builder
public record InlineButtonActionHolder(
        UUID callbackId,
        InlineButtonAction action
) {}
