package ru.ange.jointbuy.telegram.bot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    private UUID id;

    private Long chatId;

    private Integer msgId;

    private String name;

    private Float amount;

    private Boolean deleted;

    @OneToOne
    private Member purchaser;

    @OneToMany
    @JoinTable(
            name = "purchase_member",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Member> members;

}
