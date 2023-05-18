package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        return users;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        isNoEmail(user);
        boolean isEmailExists = users.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(user.getEmail()));
        if (isEmailExists) {
            throw new UserAlreadyExistException("Пользователь с указанным адресом электронной почты уже существует.");
        }
        users.add(user);
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        isNoEmail(user);

        Optional<User> optUser = users.stream().filter(a -> a.getEmail().equalsIgnoreCase(user.getEmail())).findAny();
        optUser.ifPresent(users::remove);
        users.add(user);
        return user;
    }

    private void isNoEmail(User user) {
        if (user.getEmail().isBlank() || user.getEmail().isEmpty() || user.getEmail() == null) {
            throw new InvalidEmailException("Отсутствует адрес электронной почты.");
        }
    }
}
