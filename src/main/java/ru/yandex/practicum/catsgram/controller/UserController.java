package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.model.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.model.exceptions.UserAlreadyExistException;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> findAllUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (users.contains(user)) {
            throw new UserAlreadyExistException("Пользователь с данным email уже существует.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Не указан алрес электронной почты.");
        }

        users.add(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Не указан адрес электронной почты.");
        }

        if (users.contains(user)) {
            users.remove(user);
            users.add(user);
            return user;
        } else {
            users.add(user);
            return user;
        }
    }
}
