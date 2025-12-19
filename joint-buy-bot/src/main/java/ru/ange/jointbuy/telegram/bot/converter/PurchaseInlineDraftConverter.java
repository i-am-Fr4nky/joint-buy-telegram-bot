package ru.ange.jointbuy.telegram.bot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.ange.jointbuy.telegram.bot.model.PurchaseInlineDraft;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class PurchaseInlineDraftConverter implements Converter<InlineQuery, Optional<PurchaseInlineDraft>> {

    private static final String COST_REGEX = "^\\d+\\.?\\d{1,2}";
    private static final String SPACE_REGEX = "";//"\\s";
    private static final String NAME_REGEX = "\\w+\\s*$";

    private static final Pattern COST_PATTERN = Pattern.compile(COST_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final Pattern PURCHASE_PTT = Pattern.compile(COST_REGEX + SPACE_REGEX + NAME_REGEX);


    @Override
    public Optional<PurchaseInlineDraft> convert(InlineQuery source) { // TODO add not null check

        var query = source.getQuery();
        var nameMatcher = NAME_PATTERN.matcher(query);
        var costMatcher = COST_PATTERN.matcher(query);

        if (nameMatcher.find() && costMatcher.find()) {
            var result = PurchaseInlineDraft.builder()
                    .cost(Double.parseDouble(costMatcher.group()))
                    .name(nameMatcher.group())
                    .userName(source.getFrom().getUserName())
                    .firstName(source.getFrom().getFirstName())
                    .lastName(source.getFrom().getLastName())
                    .build();
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
