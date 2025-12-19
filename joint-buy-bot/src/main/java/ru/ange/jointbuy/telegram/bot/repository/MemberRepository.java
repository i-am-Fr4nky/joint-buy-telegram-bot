package ru.ange.jointbuy.telegram.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ange.jointbuy.telegram.bot.entity.Member;
import ru.ange.jointbuy.telegram.bot.entity.Purchase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByChatIdAndTelegramUserId(Long chatId, Long telegramUserId);

    List<Member> findByChatId(Long chatId);
}
