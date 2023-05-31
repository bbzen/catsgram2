package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.UserException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final Map<Integer, Post> posts = new HashMap<>();
    private final UserService userService;
    private int id = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, String sort, Integer from) {

        return new ArrayList<>(posts.values())
                .stream()
                .sorted((p1, p2) ->{
                    int multiplier = -1;
                    if (sort.equalsIgnoreCase("asc")) {
                        multiplier = 1;
                    }
                    return Math.toIntExact(p1.getCreationDate().toEpochMilli() - p2.getCreationDate().toEpochMilli() * multiplier);
                })
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Post findById(int id) {
        if (posts.containsKey(id)) {
            return posts.get(id);
        }
        return null;
    }

    public void create(Post post) {
        Optional<User> userValue = userService.findByEmail(post.getAuthorEmail());
        if (userValue.isEmpty()) {
            throw new UserException(String.format("Пользователь %s не найден.", post.getAuthorEmail()));
        }
        post.setId(++id);
        posts.put(post.getId(), post);
    }
}
