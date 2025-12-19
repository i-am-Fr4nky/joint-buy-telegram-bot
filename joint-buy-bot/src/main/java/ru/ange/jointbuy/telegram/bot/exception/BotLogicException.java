package ru.ange.jointbuy.telegram.bot.exception;

public class BotLogicException extends RuntimeException {
    public BotLogicException(String message) {
        super(message);
    }
    public BotLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
