package ru.ange.jointbuy.telegram.bot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ange.jointbuy.telegram.bot.entity.Purchase;
import ru.ange.jointbuy.telegram.bot.model.PurchaseInlineDraft;

import java.util.Optional;

@Component
public class PurchaseDraftConverter implements Converter<Purchase, PurchaseInlineDraft> {

    @Override
    public PurchaseInlineDraft convert(Purchase source) {
        return new PurchaseInlineDraft(
                source.getAmount().doubleValue(),
                source.getName(),
                source.getPurchaser().getUserName(),
                source.getPurchaser().getFirstName(),
                source.getPurchaser().getLastName());
    }
}
