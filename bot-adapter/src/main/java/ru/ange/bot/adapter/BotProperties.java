package ru.ange.bot.adapter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public record BotProperties(
        String token,
        String username
) {
}
