package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        return users;
    }

    @PostMapping(value = "/users")
    public void create(@RequestBody User user) {
        boolean hasNoEmail = user.getEmail().isBlank() || user.getEmail().isEmpty() || user.getEmail() == null;
        if (hasNoEmail) {
            throw new InvalidEmailException("Отсутствует адрес электронной почты.");
        }

        boolean checkEmail = users.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(user.getEmail()));
        if (!checkEmail) {
            users.add(user);
        } else {
            throw new UserAlreadyExistException("Пользователь с указанным адресом электронной почты уже существует.");
        }
    }
}
