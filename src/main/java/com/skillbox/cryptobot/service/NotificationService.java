package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CryptoBot cryptoBot;

    @Value("${telegram.bot.notify.delay.value}")
    private int delayValue;

    @Value("${telegram.bot.notify.delay.unit}")
    private String delayUnit;

    @Value("${telegram.bot.notify-service.delay.value}")
    private String delayValueService;

    @Scheduled(fixedDelayString = "${telegram.bot.notify.delay.value}",
            timeUnit = TimeUnit.MINUTES)
    public void notifyUsers() {
        try {
            Double price = cryptoCurrencyService.getBitcoinPrice();
            LocalDateTime notificationBefore = LocalDateTime.now().minus(delayValue,
                    ChronoUnit.valueOf(delayUnit));
            List<User> notificationUser = userService.getAllUsersWithNotificationBefore(notificationBefore);
            notificationUser.stream()
                    .filter(user -> user.getPrice() >= price)
                    .forEach(user -> {
                        String text = "Пора покупать, стоимость биткоина "
                                + TextUtil.toString(price) + " USD";
                        cryptoBot.sendMessage(user.getTelegramId(), text);
                    });
        } catch (IOException e) {
            log.error("Error in notification service: {}", e.getMessage());
        }
    }
}
