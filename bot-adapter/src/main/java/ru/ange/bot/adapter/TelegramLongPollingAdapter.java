package ru.ange.bot.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ange.bot.adapter.processors.UpdateProcessor;

import java.util.Set;

@Component
@Slf4j
public class TelegramLongPollingAdapter extends TelegramLongPollingBot {

    private final BotProperties properties;
    private final Set<UpdateProcessor> updateProcessors;

    public TelegramLongPollingAdapter(BotProperties properties,
                                      Set<UpdateProcessor> updateProcessors) {
        super(properties.token());
        this.properties = properties;
        this.updateProcessors = updateProcessors;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var updId = update.getUpdateId();
        log.debug("Work with update {}", updId);
        for (var processor : updateProcessors) {
            var clazz = processor.getClass();
            if (processor.canProcessed(update)) {
                try {
                    log.debug("Use processor '{}' for work with update {}", clazz, updId);
                    var answer = processor.process(update);
                    if (answer.isPresent()) {
                        log.debug("Processor '{}' send answer for update {}", clazz, updId);
                        super.execute(answer.get());
                    }
                    log.debug("Processor '{}' finish work with update {}", clazz, updId);
                } catch (Exception e) {
                    log.error("Error with Processor '{}' update {}", clazz, updId, e);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return properties.username();
    }
}
