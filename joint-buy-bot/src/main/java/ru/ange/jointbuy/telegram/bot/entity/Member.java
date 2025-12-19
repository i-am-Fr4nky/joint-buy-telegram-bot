package ru.ange.jointbuy.telegram.bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private UUID id;

    private Long telegramUserId;

    private Long chatId;

    private String userName;

    private String firstName;

    private String lastName;


}
