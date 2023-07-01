package ru.yandex.practicum.catsgram.dao;

import ru.yandex.practicum.catsgram.model.Post;

import java.util.Collection;

public interface FollowDao {
    Collection<Post> getFollowFeeds(String userId, int max);
}
