package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.exception.UserNotFoundException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface CommandService {
    User getUserByTelegramId(Message message, AbsSender absSender, String commandName) throws UserNotFoundException;

    void send(Message message, AbsSender absSender, String text, String methodName);
}
