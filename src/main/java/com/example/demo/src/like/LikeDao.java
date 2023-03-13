package com.example.demo.src.like;

import com.example.demo.src.like.model.GetLikeRes;
import com.example.demo.src.review.model.GetReviewRes;
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
}
