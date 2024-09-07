package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.service.CommandService;
import com.skillbox.cryptobot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработка команды отмены подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class UnsubscribeCommand implements IBotCommand {

    private CommandService commandService;
    private UserService userService;

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        User user = commandService.getUserByTelegramId(message, absSender, getCommandIdentifier());
        if (user == null) {
            return;
        }

        user.setPrice(null);
        userService.updateUser(user.getId(), user);

        String text = "Подписка отменена";
        commandService.send(message, absSender, text, getCommandIdentifier());
    }
}