package ru.ange.jointbuy.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ange.jointbuy.telegram.bot.converter.MemberConverter;
import ru.ange.jointbuy.telegram.bot.entity.Member;
import ru.ange.jointbuy.telegram.bot.repository.MemberRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;

    public Member getOrCreate(Message message) {
//        return memberRepository.findByChatIdAndTelegramUserId(message.getChatId(), message.getFrom().getId())
//                .orElseGet(() -> memberRepository.save(newMemberFromMsg(message)));
        return getOrCreate(message.getChatId(), message.getFrom());
    }


    public Member getOrCreate(Long chatId, User user) {
        return memberRepository.findByChatIdAndTelegramUserId(chatId, user.getId())
                .orElseGet(() -> memberRepository.save(newMemberFromMsg(chatId, user)));
    }


    // TODO use settings
    public List<Member> getMembers(Long chatId) {
        return memberRepository.findByChatId(chatId);
    }

    private Member newMemberFromMsg(Long chatId, User user) {
        var result = memberConverter.convert(chatId, user); // TODO use inner method
        result.setId(UUID.randomUUID());
        return result;
    }
}
