package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class PostDaoImpl implements PostDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Post> findPostsByUser(User user) {
        String sqlQuerry = "select * from cat_post where author_id = ? order by creation_date desc";
        return jdbcTemplate.query(sqlQuerry, new RowMapper<Post>() {
            @Override
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                return PostDaoImpl.this.makePost(user, rs);
            }
        }, user.getId());
    }

    private Post makePost(User author, ResultSet rs) throws SQLException {
        // используем конструктор, методы ResultSet
        // и готовое значение author
        Integer id = rs.getInt("id");
        String description = rs.getString("description");
        String photoUrl = rs.getString("photo_url");

        // Получаем дату и конвертируем её из sql.Date в time.LocalDate
        LocalDate creationDate = rs.getDate("creation_date").toLocalDate();

        return new Post(id, author, creationDate, description, photoUrl);
    }
}
