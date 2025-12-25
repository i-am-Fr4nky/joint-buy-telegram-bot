//package ru.ange.jointbuy.telegram.bot.service;
//
//import lombok.RequiredArgsConstructor;
//import org.jvnet.hk2.annotations.Service;
//import org.springframework.data.util.Pair;
//import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import ru.ange.bot.adapter.util.ParseModes;
//import ru.ange.jointbuy.telegram.bot.model.Purchase;
//import ru.ange.jointbuy.telegram.bot.service.msg.PurchaseMsgService;
//
//@Service
//@RequiredArgsConstructor
//public class PurchaseService {
//
//    private final PurchaseMsgService purchaseMsgService;
//
//    public Pair<String, InlineKeyboardMarkup> getMsg(Purchase  purchase) {
//
////        var remitMsgCont = InputTextMessageContent.builder()
////                .parseMode(ParseModes.HTML)
////                .messageText(purchaseMsgService.getText(purchaseInlineDraft))
////                .build();
////
////        var markupInline = InlineKeyboardMarkup.builder()
////                .keyboard(purchaseMsgService.getKeys(callbackId))
////                .build();
//
////        return Pair.of(purchaseMsgService.getText(purchase), markupInline);
//    }
//}
