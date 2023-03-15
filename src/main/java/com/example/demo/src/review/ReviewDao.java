package com.example.demo.src.review;

import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PatchReviewStatusReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.user.model.PatchUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public List<GetReviewRes> getStoreUserId(int storeUserId){
        String getUsersQuery = "select * from Review where storeUserId = ?";
        int getUserParams = storeUserId;
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetReviewRes(
                        rs.getInt("reviewId"),
                        rs.getInt("storeUserId"),
                        rs.getInt("purchaseUserId"),
                        rs.getDouble("score"),
                        rs.getString("content")),
                getUserParams);
    }
    public int modifyReview(PatchReviewReq patchReviewReq){
        String modifyReviewQuery = "update Review set score =?, content =? where reviewId = ? ";
        Object[] modifyReviewParams = new Object[]{patchReviewReq.getScore(), patchReviewReq.getContent(), patchReviewReq.getReviewId()};

        return this.jdbcTemplate.update(modifyReviewQuery,modifyReviewParams);
    }
    public int modifyReviewStatus(PatchReviewStatusReq patchReviewStatusReq){
        String modifyReviewQuery = "update Review set status =? where reviewId = ? ";
        Object[] modifyReviewParams = new Object[]{patchReviewStatusReq.getStatus(), patchReviewStatusReq.getReviewId()};

        return this.jdbcTemplate.update(modifyReviewQuery,modifyReviewParams);
    }
}
