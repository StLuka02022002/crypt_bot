package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByTelegramId(Long id);
    void deleteByTelegramId(Long id);
    boolean existsByTelegramId(Long telegramID);
    @Query("SELECT u FROM User u WHERE u.price IS NOT NULL AND u.lastNotification <= :time_notification")
    List<User> findUsersWithPriceAndDate(@Param("time_notification") LocalDateTime time);
}
