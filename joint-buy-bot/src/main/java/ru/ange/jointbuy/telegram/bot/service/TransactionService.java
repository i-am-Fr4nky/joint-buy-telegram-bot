package ru.ange.jointbuy.telegram.bot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ange.jointbuy.telegram.bot.entity.Member;
import ru.ange.jointbuy.telegram.bot.entity.Purchase;
import ru.ange.jointbuy.telegram.bot.model.Transaction;
import ru.ange.jointbuy.telegram.bot.repository.PurchaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService { // TDO rename ?

    private final PurchaseRepository purchaseRepository;

    public Purchase save(Transaction transaction, Member creator, List<Member> members) {
        var purchase = Purchase.builder() // TODO use different ways for purchase and remittence
                .id(transaction.getCallbackId())
                .amount(transaction.getAmount())
                .name(transaction.getName())
                .chatId(transaction.getChatId())
                .msgId(transaction.getMsgId())
                .purchaser(creator)
                .members(members)
                .build();

        return purchaseRepository.saveAndFlush(purchase);
    }

    public List<Purchase> getPurchase(Long chatId) { // TODO use Transactional or another Data object
        return purchaseRepository.findAllByChatId(chatId);
    }

    public Purchase getPurchase(UUID id) { // TODO use Transactional or another Data object
        return purchaseRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public Purchase setDeletedByCallbackId(UUID id, boolean deleted) {
        var purchase = getPurchase(id);
        purchase.setDeleted(deleted);
        return purchaseRepository.saveAndFlush(purchase);
    }
}
