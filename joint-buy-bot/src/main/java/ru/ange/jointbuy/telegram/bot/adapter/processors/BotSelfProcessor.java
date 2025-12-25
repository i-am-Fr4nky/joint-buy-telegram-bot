package ru.ange.jointbuy.telegram.bot.adapter.processors;

import com.vdurmont.emoji.EmojiParser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.ange.bot.adapter.BotProperties;
import ru.ange.bot.adapter.processors.BotSelfMsgProcessor;
import ru.ange.bot.adapter.util.ParseModes;
import ru.ange.jointbuy.telegram.bot.converter.PurchaseEntityConverter;
import ru.ange.jointbuy.telegram.bot.converter.TransactionConverter;
import ru.ange.jointbuy.telegram.bot.service.MemberService;
import ru.ange.jointbuy.telegram.bot.service.TransactionService;
import ru.ange.jointbuy.telegram.bot.service.msg.PurchaseMsgService;

import java.util.Objects;
import java.util.Optional;


@Component
public class BotSelfProcessor extends BotSelfMsgProcessor {

    private final TransactionConverter transactionConverter;
    private final TransactionService transactionService;
    private final MemberService memberService;
    private final PurchaseMsgService purchaseMsgService;
    private final PurchaseEntityConverter purchaseEntityConverter;

    public BotSelfProcessor(BotProperties botProperties,
                            TransactionConverter transactionConverter,
                            TransactionService transactionService,
                            MemberService memberService,
                            PurchaseMsgService purchaseMsgService,
                            PurchaseEntityConverter purchaseEntityConverter) {

        super(botProperties);
        this.transactionConverter = transactionConverter;
        this.transactionService = transactionService;
        this.memberService = memberService;
        this.purchaseMsgService = purchaseMsgService;
        this.purchaseEntityConverter = purchaseEntityConverter;
    }


    /**
     * Сохраняет данные покупки в БД, добавляет в сообщение список участников
     */
    @Transactional
    @Override
    public Optional<BotApiMethod<?>> process(Update update) {
        var msg = update.getMessage();
        var transaction = Objects.requireNonNull(transactionConverter.convert(msg)); // USE
        var purchaser = memberService.getOrCreate(msg); // TODO get purchaser from member ?
        var members = memberService.getMembers(msg.getChatId());

        var purchase = transactionService.save(transaction, purchaser, members);

//        var callBackHolder = inlineButtonActionHolderConverter.convertBack(callbackQuery.getData());
//        purchaseEntityConverter.convert(purchaseEntity);
//        var text = purchaseMsgService.getText(Objects.requireNonNull(purchase));
//        transaction.getCallbackId()

//        var markupInline = InlineKeyboardMarkup.builder()
////                .keyboard(purchaseMsgService.getButtons(callbackId))
//                .build();

//        var emt = EditMessageText.builder()
//                .parseMode(ParseModes.HTML)
//                .text(text)
//                .chatId(msg.getChatId())
//                .messageId(msg.getMessageId())
////                .inlineMessageId(transaction.getInlineMessageId())
//                .replyMarkup(markupInline)
//                .build();

//        var emt = EditMessageText.builder()
//                .chatId(msg.getChatId())
//                .messageId()
//                .setMessageId( msg.getMessageId() )
                //.enableMarkdown( true )
//                .setText( EmojiParser.parseToUnicode( remMsgText ) )
//                .setReplyMarkup( markupInline );

//        return Optional.of(emt); // not need answer for inline service messages // TODO maybe need add buttons
        return Optional.empty();
    }
}
