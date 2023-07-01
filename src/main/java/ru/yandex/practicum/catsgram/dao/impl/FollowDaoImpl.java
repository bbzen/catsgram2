package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class FollowDaoImpl implements FollowDao {
    private final JdbcTemplate jdbcTemplate;

    public FollowDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Post> getFollowFeeds(String userId, int max) {
        String sqlPost = "select cp.*, cu.username, cu.nickname  from cat_post cp join cat_user cu on cu.id = cp.author_id where author_id in (select follower_id from cat_follow cf where author_id = ?) order by creation_date desc;";
        return jdbcTemplate.query(sqlPost, (rs, rowNum) -> makePost(rs), userId).stream().limit(max).collect(Collectors.toList());
    }

    private Post makePost(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String authorId = rs.getString("author_id");
        String description = rs.getString("description");
        String photoUrl = rs.getString("photo_url");
        LocalDate creationTime = rs.getDate("creation_date").toLocalDate();
        String username = rs.getString("username");
        String nickname = rs.getString("nickname");
        return new Post(id, new User(authorId, username, nickname), description, photoUrl, creationTime);
    }
}
