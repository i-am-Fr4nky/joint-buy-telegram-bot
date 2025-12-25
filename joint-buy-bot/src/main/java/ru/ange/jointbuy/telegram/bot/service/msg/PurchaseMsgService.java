package ru.ange.jointbuy.telegram.bot.service.msg;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ange.jointbuy.telegram.bot.converter.InlineButtonActionHolderConverter;
import ru.ange.jointbuy.telegram.bot.model.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PurchaseMsgService {

    private static final List<List<InlineButtonAction>> BUTTONS = List.of(
            List.of(InlineButtonAction.JOIN, InlineButtonAction.DISJOIN),
//            List.of(InlineButtonAction.EDIT), // TODO add funct
            List.of(InlineButtonAction.DELETE)
    );

    private final MessageSource messageSource;
    private final InlineButtonActionHolderConverter inlineButtonActionHolderConverter;

    public String getText(PurchaseImpl purchase) {
        // TODO добавить, если будет выводиться флажок "(все)" в сообщении
        //  c обращением в сервис, который возвращает количество пользователей в чате
        //var memersLine = "1 " + messageSource.getMessage("inline.answer.purchase.text.all.members",
        //        new Object[]{},
        //        Locale.getDefault());

        return EmojiParser.parseToUnicode(messageSource.getMessage(
                "inline.answer.purchase.text", new Object[]{
                        purchase.getName(),
                        purchase.getCost(),
                        purchase.getCreator().getUserName(),
                        purchase.getCreator().getFirstName(),
                        purchase.getCreator().getLastName(),
                        purchase.getMembers().size()
                }, Locale.getDefault()));
    }

    public String getDeletedText(Purchase purchase) {
        return EmojiParser.parseToUnicode(messageSource.getMessage(
                "inline.answer.purchase.text.deleted", new Object[]{
                        purchase.getName(),
                        purchase.getCost(),
                        purchase.getUserName(),
                        purchase.getFirstName(),
                        purchase.getLastName(),
                        "" // TODO
                }, Locale.getDefault()));
    }

    public List<List<InlineKeyboardButton>> getButtons(UUID callbackId) {
        return BUTTONS.stream()
                .map(row ->
                        row.stream()
                                .map(a -> InlineButtonActionHolder.builder()
                                        .callbackId(callbackId)
                                        .action(a)
                                        .build())
                                .map(a -> InlineKeyboardButton.builder()
                                        .text(messageSource.getMessage(
                                                a.action().getMsgCode(),
                                                new Object[]{},
                                                Locale.getDefault()))
                                        .callbackData(inlineButtonActionHolderConverter.convert(a))
                                        .build())
                                .toList())
                .toList();

    }

    public List<List<InlineKeyboardButton>> getDeletedKeys(UUID callbackId) {

        var holder = InlineButtonActionHolder.builder()
                .callbackId(callbackId)
                .action(InlineButtonAction.RESTORE)
                .build();

        var btt = InlineKeyboardButton.builder()
                .text(messageSource.getMessage(holder.action().getMsgCode(), new Object[]{}, Locale.getDefault()))
                .callbackData(inlineButtonActionHolderConverter.convert(holder))
                .build();

        return Collections.singletonList(List.of(btt));
    }

    public PurchaseInlineAware getPurchaseInlineAware(PurchaseImpl purchaseInlineDraft) {
        return new PurchaseInlineAware(
                messageSource.getMessage("inline.query.purchase.thumbnailUrl", new Object[]{}, Locale.getDefault()),
                messageSource.getMessage("inline.query.purchase.title", new Object[]{}, Locale.getDefault()),
                messageSource.getMessage("inline.query.purchase.desc",
                        new Object[]{
                                purchaseInlineDraft.getCost(),
                                purchaseInlineDraft.getName()
                        }, Locale.getDefault()));
    }
}
