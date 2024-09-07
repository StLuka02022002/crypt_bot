package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.exception.UserNotFoundException;
import com.skillbox.cryptobot.service.CommandService;
import com.skillbox.cryptobot.service.CommandServiceImpl;
import com.skillbox.cryptobot.service.UserService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscribeCommand implements IBotCommand {

    private final UserService userService;
    private final GetPriceCommand getPriceCommand;
    private final CommandService commandService;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        User user = commandService.getUserByTelegramId(message,
                absSender, getCommandIdentifier());
        if (user == null) {
            return;
        }

        Double price = getPriceFromArguments(arguments);
        if (price == null) {
            String text = """
                    Вы неправильно указали аргументы команды.
                    Правильно /subscribe 99.99
                    """;
            commandService.send(message, absSender, text, getCommandIdentifier());
            return;
        }
        user.setPrice(price);
        userService.updateUser(user.getId(), user);
        getPriceCommand.processMessage(absSender, message, null);

        String text = "Новая подписка создана на стоимость " +
                TextUtil.toString(price) + " USD";
        commandService.send(message, absSender, text, getCommandIdentifier());
    }

    private Double getPriceFromArguments(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            try {
                return Double.parseDouble(arguments[i]);
            } catch (NumberFormatException e) {
                log.error("Get bad format number from price on the command /subscribe. " +
                        "Arguments number:{} - {}", i, arguments[i]);
            }
        }
        return null;
    }
}