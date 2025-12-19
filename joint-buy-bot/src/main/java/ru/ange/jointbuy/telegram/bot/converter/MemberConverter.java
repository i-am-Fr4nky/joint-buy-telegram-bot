package ru.ange.jointbuy.telegram.bot.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ange.jointbuy.telegram.bot.entity.Member;

@Component
public class MemberConverter {

    public Member convert(Long chatId, User user) {
        return Member.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .chatId(chatId)
                .telegramUserId(user.getId())
                .build();
    }
}
