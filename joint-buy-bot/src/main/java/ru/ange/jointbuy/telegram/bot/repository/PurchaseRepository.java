package ru.ange.jointbuy.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ange.jointbuy.telegram.bot.entity.Purchase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    List<Purchase> findAllByChatId(Long chatId);

    Optional<Purchase> findById(UUID id);
}
