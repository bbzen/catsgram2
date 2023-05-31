package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "DESC", required = false) String sort) {
        if (0 < page || 0 <= size) {
            throw new IllegalArgumentException();
        } else if (!(sort.equalsIgnoreCase("asc")) || !(sort.equalsIgnoreCase("desc"))) {
            throw new IllegalArgumentException();
        }
        Integer from = page * size;
        return postService.findAll(size, sort, from);
    }

    @GetMapping("/posts/{id}")
    public Post findById(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @PostMapping(value = "/post")
    public void create(@RequestBody Post post) {
        postService.create(post);
    }
}