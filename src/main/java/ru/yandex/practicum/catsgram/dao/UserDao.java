package ru.yandex.practicum.catsgram.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

@Component
public interface UserDao {
    Optional<User> findUserById(String id);

}
