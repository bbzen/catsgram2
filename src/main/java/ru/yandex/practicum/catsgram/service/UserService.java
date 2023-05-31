package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users;
    }

    public Optional<User> findByEmail(String email) {
        return users.stream().filter(x -> x.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public User create(User user) {
        isNoEmail(user);
        boolean isEmailExists = users.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(user.getEmail()));
        if (isEmailExists) {
            throw new UserException("Пользователь с указанным адресом электронной почты уже существует.");
        }
        users.add(user);
        log.debug(user.toString());
        return user;
    }

    public User update(User user) {
        isNoEmail(user);

        Optional<User> optUser = users.stream().filter(a -> a.getEmail().equalsIgnoreCase(user.getEmail())).findAny();
        optUser.ifPresent(users::remove);
        users.add(user);
        log.debug(user.toString());
        return user;
    }

    private void isNoEmail(User user) {
        if (user.getEmail().isBlank() || user.getEmail().isEmpty() || user.getEmail() == null) {
            throw new InvalidEmailException("Отсутствует адрес электронной почты.");
        }
    }
}
