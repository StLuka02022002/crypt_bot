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

import java.time.LocalDateTime;


/**
 * Обработка команды начала работы с ботом
 */
@Service
@AllArgsConstructor
@Slf4j
public class StartCommand implements IBotCommand {

    private final UserService userService;
    private final CommandService commandService;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        createUser(message.getFrom().getId());
        String text = """
                Привет! Данный бот помогает отслеживать стоимость биткоина.
                Поддерживаемые команды:
                 /get_price - получить стоимость биткоина
                """;
        commandService.send(message, absSender,
                text, getCommandIdentifier());
    }

    private void createUser(Long userTelegramId) {
        if (!userService.existUserByTelegramId(userTelegramId)) {
            User user = new User();
            user.setTelegramId(userTelegramId);
            user.setLastNotification(LocalDateTime.now());
            userService.createUser(user);
        }
    }
}