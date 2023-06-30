package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {
    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> getFriendsFeed(@RequestBody String params) {
        ObjectMapper mapper = new ObjectMapper();
        FriendParams friendParams;
        try {
            String paramsFromBody = mapper.readValue(params, String.class);
            friendParams = mapper.readValue(paramsFromBody, FriendParams.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не верный формат json", e);
        }

        if (friendParams != null) {
            List<Post> result = new ArrayList<>();
            for (String authorId : friendParams.friends) {
                result.addAll(postService.findPostsByUser(authorId, friendParams.size, friendParams.sort));
            }
            return result;
        } else {
            throw new RuntimeException("Не верно заполнены параметры.");
        }
    }

        class FriendParams {
            String sort;
            Integer size;
            List<String> friends;

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public Integer getSize() {
                return size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public List<String> getFriends() {
                return friends;
            }

            public void setFriends(List<String> friends) {
                this.friends = friends;
            }
        }
}
