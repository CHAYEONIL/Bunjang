package com.example.demo.src.review;

import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PatchReviewStatusReq;
import com.example.demo.src.review.model.PostReviewReq;
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
        String getUsersQuery = "select R.reviewId, R.score, R.content, P.title, U.userNickName,\n" +
                "case\n" +
                "when TIMESTAMPDIFF(YEAR, R.createdAt, now()) = 1 then\n" +
                "           CONCAT(TIMESTAMPDIFF(YEAR, R.createdAt, now()), '년 전')\n" +
                "        when TIMESTAMPDIFF(MONTH, R.createdAt, now()) > 3 then\n" +
                "           CONCAT(TIMESTAMPDIFF(MONTH, R.createdAt, now()), '달 전')\n" +
                "        when TIMESTAMPDIFF(WEEK, R.createdAt, now()) >= 1 then\n" +
                "           CONCAT(TIMESTAMPDIFF(WEEK, R.createdAt, now()), '주 전')\n" +
                "        when TIMESTAMPDIFF(DAY, R.createdAt, now()) >= 1 then\n" +
                "           CONCAT(TIMESTAMPDIFF(DAY, R.createdAt, now()), '일 전')\n" +
                "else DATE_FORMAT(P.createdAt, '%p %H : %i') end as Time\n" +
                "from Review R\n" +
                "inner join Product P on R.productId = P.productId\n" +
                "inner join User U on U.userId = P.userId\n" +
                "where R.storeUserId = ?";
        int getUserParams = storeUserId;
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetReviewRes(
                        rs.getInt("reviewId"),
                        rs.getDouble("score"),
                        rs.getString("content"),
                        rs.getString("title"),
                        rs.getString("userNickName"),
                        rs.getString("Time")),
                getUserParams);
    }
    public int modifyReview(PatchReviewReq patchReviewReq){
        String modifyReviewQuery = "update Review set score =?, content =? where reviewId = ?";
        Object[] modifyReviewParams = new Object[]{patchReviewReq.getScore(), patchReviewReq.getContent(), patchReviewReq.getReviewId()};

        return this.jdbcTemplate.update(modifyReviewQuery,modifyReviewParams);
    }
    public int modifyReviewStatus(PatchReviewStatusReq patchReviewStatusReq){
        String modifyReviewQuery = "update Review set status =? where reviewId = ?";
        Object[] modifyReviewParams = new Object[]{patchReviewStatusReq.getStatus(), patchReviewStatusReq.getReviewId()};

        return this.jdbcTemplate.update(modifyReviewQuery,modifyReviewParams);
    }
}
