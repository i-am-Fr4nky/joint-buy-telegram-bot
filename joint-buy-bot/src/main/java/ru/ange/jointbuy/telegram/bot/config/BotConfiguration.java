package ru.ange.jointbuy.telegram.bot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ange.bot.adapter.BotProperties;
import ru.ange.bot.adapter.TelegramLongPollingAdapter;
import ru.ange.bot.adapter.processors.UpdateProcessor;

import java.util.Set;

@Configuration
@EnableConfigurationProperties(BotProperties.class)
public class BotConfiguration {

    @Bean
    public TelegramLongPollingAdapter telegramLongPollingAdapter(BotProperties properties,
                                                                 Set<UpdateProcessor> updateProcessors) {
        return new TelegramLongPollingAdapter(properties, updateProcessors);
    }

    @Bean
    @Profile("prod")
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingAdapter longPollingAdapter) throws TelegramApiException {
        var botApi = new TelegramBotsApi(DefaultBotSession.class);
        botApi.registerBot(longPollingAdapter);
        return botApi;
    }
}
