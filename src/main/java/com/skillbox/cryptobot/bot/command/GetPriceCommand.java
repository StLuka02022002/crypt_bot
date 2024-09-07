package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.service.CommandService;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.UserService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Обработка команды получения текущей стоимости валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class GetPriceCommand implements IBotCommand {

    private final CryptoCurrencyService service;
    private final CommandService commandService;
    private final UserService userService;

    @Override
    public String getCommandIdentifier() {
        return "get_price";
    }

    @Override
    public String getDescription() {
        return "Возвращает цену биткоина в USD";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        User user = commandService.getUserByTelegramId(message,
                absSender, getCommandIdentifier());
        if (user == null) {
            return;
        }
        try {
            String text = "Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD";
            commandService.send(message, absSender, text, getCommandIdentifier());
            user.setLastNotification(LocalDateTime.now());
            userService.updateUser(user.getId(), user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}