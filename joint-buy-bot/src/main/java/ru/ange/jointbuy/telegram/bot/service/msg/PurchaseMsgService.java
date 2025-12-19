package ru.ange.jointbuy.telegram.bot.service.msg;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ange.jointbuy.telegram.bot.converter.InlineButtonActionHolderConverter;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonAction;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonActionHolder;
import ru.ange.jointbuy.telegram.bot.model.PurchaseInlineAware;
import ru.ange.jointbuy.telegram.bot.model.PurchaseInlineDraft;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseMsgService {

    private static final List<InlineButtonAction> AVAILABLE_ACTIONS = List.of(
            InlineButtonAction.JOIN, // TODO add funct
//            InlineButtonAction.EDIT, // TODO add funct
            InlineButtonAction.DELETE);

    private final MessageSource messageSource;
    private final InlineButtonActionHolderConverter inlineButtonActionHolderConverter;

    public String getText(PurchaseInlineDraft purchase) {
        return EmojiParser.parseToUnicode(messageSource.getMessage(
                "inline.answer.purchase.text", new Object[]{
                        purchase.name(),
                        purchase.cost(),
                        purchase.userName(),
                        purchase.firstName(),
                        purchase.lastName()
                }, Locale.getDefault()));
    }

    public String getDeletedText(PurchaseInlineDraft purchase) {
        return EmojiParser.parseToUnicode(messageSource.getMessage(
                "inline.answer.purchase.text.deleted", new Object[]{
                        purchase.name(),
                        purchase.cost(),
                        purchase.userName(),
                        purchase.firstName(),
                        purchase.lastName()
                }, Locale.getDefault()));
    }

    public List<List<InlineKeyboardButton>> getKeys(UUID callbackId) {
        return AVAILABLE_ACTIONS.stream()
                .map(a ->
                        InlineButtonActionHolder.builder()
                                .callbackId(callbackId)
                                .action(a)
                                .build())
                .map(a ->
                        InlineKeyboardButton.builder()
                                .text(messageSource.getMessage(a.action().getMsgCode(), new Object[]{}, Locale.getDefault()))
                                .callbackData(inlineButtonActionHolderConverter.convert(a))
                                .build())
                .map(List::of)
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

    public PurchaseInlineAware getPurchaseInlineAware(PurchaseInlineDraft purchaseInlineDraft) {
        return new PurchaseInlineAware(
                messageSource.getMessage("inline.query.purchase.thumbnailUrl", new Object[]{}, Locale.getDefault()),
                messageSource.getMessage("inline.query.purchase.title", new Object[]{}, Locale.getDefault()),
                messageSource.getMessage("inline.query.purchase.desc",
                        new Object[]{
                                purchaseInlineDraft.cost(),
                                purchaseInlineDraft.name()
                        }, Locale.getDefault()));
    }
}
