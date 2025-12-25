package ru.ange.jointbuy.telegram.bot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ange.jointbuy.telegram.bot.entity.Member;
import ru.ange.jointbuy.telegram.bot.model.UserImpl;

@Component
public class UserImplConverter implements Converter<Member, UserImpl> {

    @Override
    public UserImpl convert(Member source) {
        return UserImpl.builder()
                .userName(source.getUserName())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
