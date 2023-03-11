package com.example.demo.src.review;

import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createReview(PostReviewReq postReviewReq) {
        String createReviewQuery = "insert into Review(storeUserId, purchaseUserId, score, content) VALUES (?,?,?,?)";
        Object[] createReviewParams = new Object[]{postReviewReq.getStoreUserId(), postReviewReq.getPurchaseUserId(), postReviewReq.getScore(), postReviewReq.getContent()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
}
