package ru.ange.jointbuy.telegram.bot.adapter.processors;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ange.bot.adapter.BotProperties;
import ru.ange.bot.adapter.processors.BotSelfMsgProcessor;
import ru.ange.jointbuy.telegram.bot.converter.TransactionConverter;
import ru.ange.jointbuy.telegram.bot.service.MemberService;
import ru.ange.jointbuy.telegram.bot.service.TransactionService;

import java.util.Objects;
import java.util.Optional;

@Component
public class BotSelfProcessor extends BotSelfMsgProcessor {

    private final TransactionConverter transactionConverter;
    private final TransactionService transactionService;
    private final MemberService memberService;

    public BotSelfProcessor(BotProperties botProperties,
                            TransactionConverter transactionConverter,
                            TransactionService transactionService,
                            MemberService memberService) {

        super(botProperties);
        this.transactionConverter = transactionConverter;
        this.transactionService = transactionService;
        this.memberService = memberService;
    }

    @Transactional
    @Override
    public Optional<BotApiMethod<?>> process(Update update) {
        var msg = update.getMessage();
        var transaction = Objects.requireNonNull(transactionConverter.convert(msg)); // USE
        var purchaser = memberService.getOrCreate(msg); // TODO get purchaser from member ?
        var members = memberService.getMembers(msg.getChatId());

        transactionService.save(transaction, purchaser, members);
        return Optional.empty(); // not need answer for inline service messages // TODO maybe need add buttons
    }
}
