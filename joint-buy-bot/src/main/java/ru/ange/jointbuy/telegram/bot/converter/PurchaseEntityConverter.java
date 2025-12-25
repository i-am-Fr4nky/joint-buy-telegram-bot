package ru.ange.jointbuy.telegram.bot.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ange.jointbuy.telegram.bot.entity.Purchase;
import ru.ange.jointbuy.telegram.bot.model.PurchaseImpl;

@Component
@RequiredArgsConstructor
public class PurchaseEntityConverter implements Converter<Purchase, PurchaseImpl> {

    private final UserImplConverter implConverter;

    @Override
    public PurchaseImpl convert(Purchase source) {
        var members = source.getMembers().stream()
                .map(implConverter::convert)
                .toList();
        return PurchaseImpl.builder()
                .cost(source.getAmount().doubleValue())
                .name(source.getName())
                .creator(implConverter.convert(source.getPurchaser()))
                .members(members)
                .build();
    }
}
