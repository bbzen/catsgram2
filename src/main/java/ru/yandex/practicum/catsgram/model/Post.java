package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Post {
    private Integer id;
    private final String authorEmail; // автор
    private final Instant creationDate = Instant.now(); // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии

    public Post( String authorEmail, String description, String photoUrl) {
        this.authorEmail = authorEmail;
        this.description = description;
        this.photoUrl = photoUrl;
    }

}