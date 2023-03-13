package com.example.demo.src.like;

import com.example.demo.src.like.model.GetLikeRes;
import com.example.demo.src.like.model.PostLikeReq;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class LikeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetLikeRes> getuserId(int userId){
        String getUsersQuery = "select * from 14_TEST.Like where userId = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetLikeRes(
                        rs.getInt("likeId"),
                        rs.getInt("userId"),
                        rs.getInt("productId")),
                getUserParams);
    }

    public int createLike(PostLikeReq postLikeReq) {
        String createLikeQuery = "insert into 14_TEST.Like(userId, productId) VALUES (?,?)";
        Object[] createLikeParams = new Object[]{postLikeReq.getUserId(), postLikeReq.getProductId()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
}
