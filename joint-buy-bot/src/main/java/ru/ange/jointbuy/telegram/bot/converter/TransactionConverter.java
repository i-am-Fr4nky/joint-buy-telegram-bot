package ru.ange.jointbuy.telegram.bot.converter;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonActionHolder;
import ru.ange.jointbuy.telegram.bot.model.Transaction;

import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionConverter implements Converter<ChosenInlineQuery, Transaction> {

    private final MessageSource messageSource; // TODO может стоит использовать property в аргументах
    private final InlineButtonActionHolderConverter inlineButtonActionHolderConverter;

    // TODO использовать отдельный сервис для получения сообщений о покупке и их конвретации обратно
    //  или добавить метод convert back
    @Override
    public Transaction convert(ChosenInlineQuery source) {

        var resultId = source.getResultId();
        var inlineMsgId = source.getInlineMessageId();
        var query = source.getQuery();

        var message = messageSource.getMessage("inline.answer.purchase.text", null, Locale.getDefault());
        var args = StringUtils.difference(message, query).split(" ");

        return Transaction.builder()
                .name(args[1]) // TODO add checks
                .amount(Float.valueOf(args[0])) // TODO add checks
//                .telegramInlineMsgId(inlineMsgId)
                .build();
    }

    public Transaction convert(Message source) {

//        var resultId = source.getResultId();
//        var inlineMsgId = source.getInlineMessageId();
//        var query = source.getQuery();

//        var cc = removeMarkDown(EmojiParser.parseToUnicode(text));
//
//        var message = messageSource.getMessage("inline.answer.purchase.text", null, Locale.getDefault());
//        var args = StringUtils.difference(message, cc).split(" ");

        var args = source.getEntities().stream()
                .filter(e -> e.getType().equals("bold"))
                .map(MessageEntity::getText)
                .toList();


        return Transaction.builder()
                .name(args.get(0)) // TODO add checks
                .amount(Float.valueOf(args.get(1).replace(" ", ""))) // TODO add checks
                .callbackId(getCallbackId(source))
                .chatId(source.getChatId())
                .msgId(source.getMessageId())
                .build();
    }

    private UUID getCallbackId(Message source) {
        var callbackIds = source.getReplyMarkup().getKeyboard().stream()
                .flatMap(Collection::stream)
                .map(InlineKeyboardButton::getCallbackData)
                .map(inlineButtonActionHolderConverter::convertBack)
                .map(InlineButtonActionHolder::callbackId)
                .collect(Collectors.toSet());

        if (callbackIds.size() > 1) { // Проверка на одинаковые id
            throw new RuntimeException(); // TODO use special Exception
        }

        return  callbackIds.stream().findFirst().orElseThrow();
    }


    private String removeMarkDown(String text) {
        return text
                .replace("'", "")
                .replace("*", "");
    }
}
