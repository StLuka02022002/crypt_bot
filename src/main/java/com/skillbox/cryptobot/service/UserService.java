package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(UUID id);
    User getUserByTelegramId(Long telegramId);
    List<User> getAllUsers();
    List<User> getAllUsersWithNotificationBefore(LocalDateTime time);
    User createUser(User user);
    User updateUser(UUID id,User user);
    User updateUserByTelegramId(Long telegramId, User user);
    void deleteUser(UUID id);
    void deleteUserByTelegramId(Long id);
    boolean existUser(UUID id);
    boolean existUserByTelegramId(Long telegramID);
}
