package com.skillbox.cryptobot.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(exclude = "id")
@Table(name = "subscribers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "telegram_id",nullable = false)
    private Long telegramId;

    @Column(name = "price")
    private Double price;

    @Column(name = "lastNotification")
    private LocalDateTime lastNotification;
}
