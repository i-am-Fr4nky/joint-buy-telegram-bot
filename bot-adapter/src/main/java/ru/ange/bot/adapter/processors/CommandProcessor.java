package ru.ange.bot.adapter.processors;

import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CommandProcessor implements UpdateProcessor {

    private final String command;

    public CommandProcessor(String command) {
        this.command = command;
    }

    @Override
    public boolean canProcessed(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasEntities()) {
            return false;
        }
        return update.getMessage()
                .getEntities()
                .stream()
                .filter(e -> EntityType.BOTCOMMAND.equals(e.getType()))
                .anyMatch(e -> extractCommand(e).equals(command));
    }

    protected String extractCommand(MessageEntity command) {
        var commandTest = command.getText().substring(1);
        var mentionIndex = commandTest.indexOf('@');
        if (mentionIndex != -1) {
            return commandTest.substring(0, mentionIndex);
        }
        return commandTest;
    }

}
