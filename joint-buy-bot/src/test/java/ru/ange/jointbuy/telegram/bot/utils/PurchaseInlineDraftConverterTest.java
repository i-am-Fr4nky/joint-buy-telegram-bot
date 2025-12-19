package ru.ange.jointbuy.telegram.bot.utils;

import org.junit.jupiter.api.Test;
import ru.ange.jointbuy.telegram.bot.converter.PurchaseInlineDraftConverter;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseInlineDraftConverterTest {

    private final PurchaseInlineDraftConverter purchaseInlineDraftConverter = new PurchaseInlineDraftConverter();

    @Test
    void convert() {
        var msg = "10 name";
//        var draft = purchaseInlineDraftConverter.convert(msg);
//        assertNotNull(draft);
    }
}