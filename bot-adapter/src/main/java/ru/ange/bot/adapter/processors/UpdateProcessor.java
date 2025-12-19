package ru.ange.bot.adapter.processors;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface UpdateProcessor {

    boolean processed(Update update); // TODO use predicate ?

    Optional<BotApiMethod<?>> process(Update update);

}
