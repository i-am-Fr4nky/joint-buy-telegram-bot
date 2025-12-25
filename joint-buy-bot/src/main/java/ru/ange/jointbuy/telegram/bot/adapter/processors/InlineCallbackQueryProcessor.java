package ru.ange.jointbuy.telegram.bot.adapter.processors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.ange.bot.adapter.processors.UpdateProcessor;
import ru.ange.bot.adapter.util.ParseModes;
import ru.ange.jointbuy.telegram.bot.converter.InlineButtonActionHolderConverter;
import ru.ange.jointbuy.telegram.bot.converter.PurchaseDraftConverter;
import ru.ange.jointbuy.telegram.bot.entity.Purchase;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonActionHolder;
import ru.ange.jointbuy.telegram.bot.service.MemberService;
import ru.ange.jointbuy.telegram.bot.service.TransactionService;
import ru.ange.jointbuy.telegram.bot.service.msg.PurchaseMsgService;

import java.util.*;

@Component
@RequiredArgsConstructor
public class InlineCallbackQueryProcessor implements UpdateProcessor {

    private final InlineButtonActionHolderConverter inlineButtonActionHolderConverter;
    private final TransactionService transactionService;
    private final PurchaseDraftConverter purchaseDraftConverter;
    private final PurchaseMsgService purchaseMsgService;
    private final MemberService memberService;

    @Override
    public boolean canProcessed(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    @Transactional
    public Optional<BotApiMethod<?>> process(Update update) {

        var callbackQuery = update.getCallbackQuery();
        var callBackHolder = inlineButtonActionHolderConverter.convertBack(callbackQuery.getData());

        switch (callBackHolder.action()) {
            case DELETE -> { // TODO проверить как работает в личных сообщениях
                var purchase = transactionService.setDeletedByCallbackId(callBackHolder.callbackId(), true);
                return createDeletedMsg(purchase, callBackHolder, callbackQuery);
            }
            case RESTORE -> {
                var purchase = transactionService.setDeletedByCallbackId(callBackHolder.callbackId(), false);
                return createRestoredMsg(purchase, callBackHolder, callbackQuery);
            }
            case JOIN -> {
                var purchase = transactionService.getPurchase(callBackHolder.callbackId());
                var members = purchase.getMembers();
                var selmSelf = members.stream()
                        .filter(m -> m.getTelegramUserId().equals(callbackQuery.getFrom().getId()))
                        .findFirst();

                if (selmSelf.isPresent()) {
                    var acq = AnswerCallbackQuery.builder()
                            .callbackQueryId(callbackQuery.getId())
                            .text("Вы уже участвуете в этой покупке") // TODO use message properties
                            .build();
                    return Optional.of(acq);
                }

                purchase.getMembers().add(memberService.getOrCreate(purchase.getChatId(), callbackQuery.getFrom()));

                // TODO исправлять сообщение вместо алерта, если в нем будет список покупателей.
                var acq = AnswerCallbackQuery.builder()
                        .callbackQueryId(callbackQuery.getId())
                        .text("Вы присоединились к покупке") // TODO use message properties
                        .build();

                return Optional.of(acq);
            } default -> {
                throw new RuntimeException("Unknow action"); // TODO use custom Exception
            }
        }
    }


    private Optional<BotApiMethod<?>> createDeletedMsg(
            Purchase purchase,
            InlineButtonActionHolder callBackHolder,
            CallbackQuery callbackQuery) {

        var text = purchaseMsgService.getDeletedText(
                Objects.requireNonNull(purchaseDraftConverter.convert(purchase)));

        var markupInline = InlineKeyboardMarkup.builder()
                .keyboard(purchaseMsgService.getDeletedKeys(callBackHolder.callbackId()))
                .build();

        var emt = EditMessageText.builder()
                .parseMode(ParseModes.HTML)
                .inlineMessageId(callbackQuery.getInlineMessageId())
                .text(text)
                .replyMarkup(markupInline)
                .build();

        return Optional.of(emt);
    }

    private Optional<BotApiMethod<?>> createRestoredMsg(
            Purchase purchase,
            InlineButtonActionHolder callBackHolder,
            CallbackQuery callbackQuery) {

//        var text = purchaseMsgService.getText(
//                Objects.requireNonNull(purchaseDraftConverter.convert(purchase)));

        var text = "some text";

        var markupInline = InlineKeyboardMarkup.builder()
                .keyboard(purchaseMsgService.getButtons(callBackHolder.callbackId()))
                .build();

        var emt = EditMessageText.builder()
                .parseMode(ParseModes.HTML)
                .inlineMessageId(callbackQuery.getInlineMessageId())
                .text(text)
                .replyMarkup(markupInline)
                .build();
        return Optional.of(emt);
    }

}
