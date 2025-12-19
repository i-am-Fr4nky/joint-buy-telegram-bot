package ru.ange.bot.adapter.processors;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ange.bot.adapter.BotProperties;
import ru.ange.bot.adapter.util.TelegramConstants;

public abstract class BotSelfMsgProcessor implements UpdateProcessor {

    private final BotProperties botProperties;

    public BotSelfMsgProcessor(BotProperties botProperties) {
        this.botProperties = botProperties;
    }

    @Override
    public boolean processed(Update update) {
        if (!update.hasMessage())
            return false;

        var msg = update.getMessage();
        return msg.hasViaBot() && msg.getViaBot().getUserName()
                .equals(botProperties.username() + TelegramConstants.TELEGRAM_BOT_NAME_ENDING);
    }

}
