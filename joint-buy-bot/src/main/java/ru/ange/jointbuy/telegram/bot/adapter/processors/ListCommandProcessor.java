package ru.ange.jointbuy.telegram.bot.adapter.processors;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ange.bot.adapter.processors.CommandProcessor;
import ru.ange.bot.adapter.util.ParseModes;
import ru.ange.bot.adapter.util.TelegramConstants;
import ru.ange.jointbuy.telegram.bot.model.COMMAND;
import ru.ange.jointbuy.telegram.bot.service.TransactionService;

import java.util.Optional;

@Component
public class ListCommandProcessor extends CommandProcessor {

    public static final String LIST_PURCHASES_MSG_PTT = "Список покупок:%s\n\n `ИТОГО (%s) : ` *%s* \u20BD";
    public static final String LIST_PURCHASE_LINE_PTT = "\n:euro: %s \u20BD : \uD83C\uDFF7️ [%s](https://t.me/c/%d/%d)"; // TODO use msg ptt

    private final TransactionService transactionService;

    public ListCommandProcessor(TransactionService transactionService) {
        super(COMMAND.LIST.getCommand()); // TODO use Const or property
        this.transactionService = transactionService;
    }

    @Override
    public Optional<BotApiMethod<?>> process(Update update) {

        var msg = update.getMessage();
        var purchases = transactionService.getPurchase(msg.getChatId());

        // TODO use converter from purchases to MSG (but need chat id)
        if (purchases.isEmpty()) {
            return Optional.empty(); // TODO вернуть сообщение что покупок нет
        }

        // TODO добавить логику с расчетом пробелов перед total из JointBuyBot 1

        var varPurchasesList = new StringBuilder();
        var total = 0F;
        for (var purchase : purchases) {
            var line = String.format(LIST_PURCHASE_LINE_PTT, // TODO use messages properties
                    purchase.getAmount(),
                    purchase.getName(),
                    convertForMsgLink(purchase.getChatId()),
                    purchase.getMsgId());

            varPurchasesList.append(line);
            total += purchase.getAmount();
        }

        var text = String.format(LIST_PURCHASES_MSG_PTT,
                varPurchasesList, purchases.size(), total);

        var ansMsg = SendMessage.builder()
                .text(EmojiParser.parseToUnicode(text))
                .chatId(msg.getChatId())
                .parseMode(ParseModes.MARKDOWN)
                .build();

        return Optional.of(ansMsg);
    }

    private Long convertForMsgLink(Long chatId) {
        var str = chatId.toString().replace(TelegramConstants.TELEGRAM_CHAT_ID_PREFIX, "");
        return Long.parseLong(str);
    }
}
