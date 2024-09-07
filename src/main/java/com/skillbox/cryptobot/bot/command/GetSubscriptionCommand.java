package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.service.CommandService;
import com.skillbox.cryptobot.service.UserService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {
    private final UserService userService;
    private final CommandService commandService;

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
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
        if (user.getPrice() == null) {
            String text = "Активные подписки отсутствуют";
            commandService.send(message, absSender, text, getCommandIdentifier());
            return;
        }
        String text = "Вы подписаны на стоимость биткоина " +
                TextUtil.toString(user.getPrice()) + " USD";

        commandService.send(message, absSender, text, getCommandIdentifier());
    }
}