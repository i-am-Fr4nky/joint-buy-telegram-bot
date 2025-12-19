package ru.ange.jointbuy.telegram.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
public class Transaction {

    private Long chatId;

    private Integer msgId;

    private String name;

    private Float amount;

    private UUID callbackId;
}
