package ru.ange.jointbuy.telegram.bot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonAction;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonActionHolder;

import java.util.UUID;

@Component
public class InlineButtonActionHolderConverter implements Converter<InlineButtonActionHolder, String> {

    private static final String DELIMITER = "_";

    @Override
    public String convert(InlineButtonActionHolder source) {
        return source.action() + DELIMITER + source.callbackId();
    }

    public InlineButtonActionHolder convertBack(String source) {
        var params = source.split(DELIMITER);
        return InlineButtonActionHolder.builder()
                .action(InlineButtonAction.valueOf(params[0]))
                .callbackId(UUID.fromString(params[1]))
                .build();
    }
}
