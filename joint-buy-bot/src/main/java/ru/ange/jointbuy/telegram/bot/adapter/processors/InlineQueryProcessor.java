package ru.ange.jointbuy.telegram.bot.adapter.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.ange.bot.adapter.processors.UpdateProcessor;
import ru.ange.bot.adapter.util.ParseModes;
import ru.ange.jointbuy.telegram.bot.converter.PurchaseInlineDraftConverter;
import ru.ange.jointbuy.telegram.bot.model.PurchaseInlineDraft;
import ru.ange.jointbuy.telegram.bot.service.msg.PurchaseMsgService;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InlineQueryProcessor implements UpdateProcessor {

    private final PurchaseInlineDraftConverter purchaseInlineDraftConverter;
    private final PurchaseMsgService purchaseMsgService;

    @Override
    public boolean processed(Update update) {
        return update.hasInlineQuery();
    }

    @Override
    public Optional<BotApiMethod<?>> process(Update update) {

        var inlineQuery = update.getInlineQuery();
        var answersButtons = new ArrayList<InlineQueryResult>();
        var callbackId = UUID.randomUUID();

        var purchaseInlineDraft = Objects.requireNonNull(purchaseInlineDraftConverter.convert(inlineQuery));
        purchaseInlineDraft.ifPresent(inlineDraft ->
                answersButtons.add(getPurchaseInputTextMessageContent(inlineDraft, callbackId)));

        if (answersButtons.isEmpty()) {
            return Optional.empty();
        } else {
            var answerInlineQuery = AnswerInlineQuery.builder()
                    .inlineQueryId(inlineQuery.getId())
                    .results(answersButtons)
                    .build();
            return Optional.of(answerInlineQuery);
        }
    }

    private InlineQueryResultArticle getPurchaseInputTextMessageContent(
            PurchaseInlineDraft purchaseInlineDraft,
            UUID callbackId) {

        var remitMsgCont = InputTextMessageContent.builder()
                .parseMode(ParseModes.HTML)
                .messageText(purchaseMsgService.getText(purchaseInlineDraft))
                .build();

        var markupInline = InlineKeyboardMarkup.builder()
                .keyboard(purchaseMsgService.getKeys(callbackId))
                .build();

        var purchaseAware = purchaseMsgService.getPurchaseInlineAware(purchaseInlineDraft);

        // TODO use converters ?
        return InlineQueryResultArticle.builder()
                .inputMessageContent(remitMsgCont)
                .id(callbackId.toString()) // Кажется можно испольовать для передачи типа
                .thumbnailUrl(purchaseAware.thumbnailUrl())
                .title(purchaseAware.title())
                .description(purchaseAware.description())
                .replyMarkup(markupInline)
                .build();
    }
}
