package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {
    private final UserService userService;

    public User getUserByTelegramId(Message message, AbsSender absSender, String commandName) {
        Long userTelegramId = message.getFrom().getId();
        User user = userService.getUserByTelegramId(userTelegramId);
        if (user == null) {
            log.error("User with userTelegramId:{} not found on the command /{}", userTelegramId, commandName);
            String text = "Мы не нашли Вас в нашей базе!";
            send(message, absSender, text, commandName);
        }
        return user;
    }

    public void send(Message message, AbsSender absSender, String text, String methodName) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText(text);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /{} command", methodName, e);
        }
    }
}
