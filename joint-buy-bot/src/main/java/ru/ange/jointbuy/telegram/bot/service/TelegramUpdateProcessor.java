package ru.ange.jointbuy.telegram.bot.service;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import ru.ange.jointbuy.telegram.bot.config.BotProperties;
import ru.ange.jointbuy.telegram.bot.converter.InlineButtonActionHolderConverter;
import ru.ange.jointbuy.telegram.bot.converter.MemberConverter;
import ru.ange.jointbuy.telegram.bot.converter.PurchaseInlineDraftConverter;
import ru.ange.jointbuy.telegram.bot.converter.TransactionConverter;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonAction;
import ru.ange.jointbuy.telegram.bot.model.InlineButtonActionHolder;
import ru.ange.jointbuy.telegram.bot.model.PurchaseInlineDraft;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TelegramUpdateProcessor { // TODO rename as facade

    private final PurchaseInlineDraftConverter purchaseInlineDraftConverter;
    private final MessageSource messageSource;

    private final TransactionConverter transactionConverter;
    private final TransactionService transactionService;
    private final MemberConverter memberConverter;

    private final InlineButtonActionHolderConverter inlineButtonActionHolderConverter;


//    private final BotProperties properties;





    private final MemberService memberService;

    public Optional<BotApiMethod<?>> processUpdate(Update update) {

        if (update.hasInlineQuery()) {

        } else if (update.hasChosenInlineQuery()) {

//            var transaction = transactionConverter.convert(update.getChosenInlineQuery());
//            transactionService.save(Objects.requireNonNull(transaction));
//            return Optional.empty(); // not need answer for inline service messages // TODO maybe need add buttons

        } else if (update.hasCallbackQuery()) {

//            var callbackQuery = update.getCallbackQuery();
//            var callBackHolder = inlineButtonActionHolderConverter.convertBack(callbackQuery.getData());
//
//            switch (callBackHolder.action()) {
//                case DELETE -> {
//                    var deletedPurchase = transactionService.setDeletedTrueByCallbackId(callBackHolder.callbackId());
//
//                    var msgText = EmojiParser.parseToUnicode(messageSource.getMessage(
//                            "inline.answer.purchase.text.deleted", new Object[]{
//                                    deletedPurchase.getName(),
//                                    deletedPurchase.getAmount(),
//                                    deletedPurchase.getPurchaser().getUserName(),
//                                    deletedPurchase.getPurchaser().getFirstName(),
//                                    deletedPurchase.getPurchaser().getLastName(),
//                            }, Locale.getDefault()));
//
//                    var emt = EditMessageText.builder()
//                            .parseMode("Markdown") // TODO use const
//                            .chatId(deletedPurchase.getChatId())
//                            .messageId(deletedPurchase.getMsgId())
//                            .text(msgText)
////                    .setReplyMarkup( markupInline ); // TODO add remove btt
//                            .build();
//                    return Optional.of(emt);
//                }
//                default -> {
//                    throw new RuntimeException("Unknow action"); // TODO use custom Exception
//                }
//            }


        } else if (update.hasMessage()) {

            var message = update.getMessage();
//            if (message.hasViaBot() && message.getViaBot().getUserName().equals(properties.username() + "Bot")) { // TODO use const
            if (message.hasViaBot() && message.getViaBot().getUserName().equals("Bot")) { // TODO use const

//                // TODO use service for @Transactional
//                var transaction = Objects.requireNonNull(transactionConverter.convert(message));
//                var purchaser = memberService.getOrCreate(message); // TODO get purchaser from member ?
//                var members = memberService.getMembers(message.getChatId());
//
//                transactionService.save(transaction, purchaser, members);
//                return Optional.empty(); // not need answer for inline service messages // TODO maybe need add buttons
            }

            if (CollectionUtils.isNotEmpty(message.getNewChatMembers())) {
//                return StateMachineEvents.NEW_MEMBERS; // TODO add new members in BD
            } else if (CollectionUtils.isNotEmpty(message.getEntities())) {
                var command = message
                        .getEntities()
                        .stream()
                        .filter(e -> EntityType.BOTCOMMAND.equals(e.getType()))
                        .findFirst();

                if (command.isPresent()) {
//                    var com = Arrays.stream(COMMAND.values()).findFirst().; // TODO get bu value
//                    if (com == COMMAND.LIST) {

//                    var purchases = transactionService.getPurchase(message.getChatId());
//
//                    // TODO use converter from purchases to MSG (but need chat id)
//                    if (purchases.isEmpty()) {
//                        return Optional.empty(); // TODO вернуть сообщение что покупок нет
//                    }
//
//                    // TODO добавить логику с расчетом пробелов перед total из JointBuyBot 1
//
//                    var varPurchasesList = new StringBuilder();
//                    var total = 0F;
//                    for (var purchase : purchases) {
//                        var line = String.format(LIST_PURCHASE_LINE_PTT, // TODO use messages properties
//                                purchase.getAmount(),
//                                purchase.getName(),
//                                convertForMsgLink(purchase.getChatId()),
//                                purchase.getMsgId());
//
//                        varPurchasesList.append(line);
//                        total += purchase.getAmount();
//                    }
//
//                    var text = String.format(LIST_PURCHASES_MSG_PTT,
//                            varPurchasesList, purchases.size(), total);
//
//                    var msg = SendMessage.builder()
//                            .text(EmojiParser.parseToUnicode(text))
//                            .chatId(message.getChatId())
//                            .parseMode("Markdown") // TODO use const
//                            .build();
//
//                    return Optional.of(msg);
                }
            }
        }
        return Optional.empty();
    }

    // TODO use utility class







//    private final StateMachineService stateMachineService;
//
//    public TelegramUpdateProcessor(StateMachineService stateMachineService) {
//        this.stateMachineService = stateMachineService;
//    }
//
//    public void processUpdate(Update update) {
//        String machineId = getMachineId(update);
//        if (machineId == null) {
//            return;
//        }
//        stateMachineService.sendToStateMachine(machineId, createMessage(update));
//    }
//
//    private String getMachineId(Update update) {
//        if (update.hasMessage()) {
//            return update.getMessage().getChatId().toString();
//        } else if (update.hasCallbackQuery()) {
//            return update.getCallbackQuery().getMessage().getChatId().toString();
//        }
//        return null;
//    }
//
//    private Message<StateMachineEvents> createMessage(Update update) {
//        var event = extractEvent(update);
//        var headers = new MessageHeaders(Map.of(
//                TelegramMessageHeaders.UPDATE, update
//        ));
//        return new GenericMessage<>(event, headers);
//    }
//
//    private StateMachineEvents extractEvent(Update update) {
//        var message = update.getMessage();
//        if (message != null) {
//            if (CollectionUtils.isNotEmpty(message.getNewChatMembers())) {
//                return StateMachineEvents.NEW_MEMBERS;
//            } else if (CollectionUtils.isNotEmpty(message.getEntities())) {
//                var command = message
//                        .getEntities()
//                        .stream()
//                        .filter(e -> EntityType.BOTCOMMAND.equals(e.getType())).findFirst();
//                if (command.isPresent()) {
//                    return EnumUtils.getEnum(StateMachineEvents.class, StateMachineEvents.COMMAND_PREFIX + extractCommand(command.get()).toUpperCase(), StateMachineEvents.UNKNOWN);
//                }
//            } else if (StringUtils.isNotEmpty(message.getText())) {
//                return StateMachineEvents.TEXT_INPUT;
//            }
//        } else if (update.hasCallbackQuery()) {
//            var query = update.getCallbackQuery();
//            String[] split = query.getData().split("\\" + CallbackConstants.CALLBACK_SEPARATOR, 2);
//            return EnumUtils.getEnum(StateMachineEvents.class, StateMachineEvents.CALLBACK_PREFIX + split[0].toUpperCase(), StateMachineEvents.UNKNOWN);
//        }
//        return StateMachineEvents.UNKNOWN;
//    }
//
    private static String extractCommand(MessageEntity command) {
        var commandTest = command.getText().substring(1);
        var mentionIndex = commandTest.indexOf('@');
        if (mentionIndex != -1) {
            return commandTest.substring(0, mentionIndex);
        }
        return commandTest;
    }


}
