package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.User;
import com.skillbox.cryptobot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsersWithNotificationBefore(LocalDateTime time) {
        return userRepository.findUsersWithPriceAndDate(time);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public User updateUserByTelegramId(Long telegramId, User user) {
        User findUser = getUserByTelegramId(telegramId);
        user.setId(findUser.getId());
        return userRepository.save(user);
    }

    @Override
    public boolean existUser(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existUserByTelegramId(Long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserByTelegramId(Long id) {
        userRepository.deleteByTelegramId(id);
    }
}
