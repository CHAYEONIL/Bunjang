package com.example.demo.src.like;

import com.example.demo.src.like.model.GetLikeRes;
import com.example.demo.src.like.model.PatchLikeStatusReq;
import com.example.demo.src.like.model.PostLikeReq;
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
        String getUsersQuery = "select L.likeId, P.title, P.price, U.userNickName, PI.imageUrl, \n" +
                "case\n" +
                "when TIMESTAMPDIFF(YEAR, P.createdAt, now()) = 1 then\n" +
                "           CONCAT(TIMESTAMPDIFF(YEAR, P.createdAt, now()), '년 전')\n" +
                "        when TIMESTAMPDIFF(MONTH, P.createdAt, now()) > 3 then\n" +
                "           CONCAT(TIMESTAMPDIFF(MONTH, P.createdAt, now()), '달 전')\n" +
                "        when TIMESTAMPDIFF(WEEK, P.createdAt, now()) >= 1 then\n" +
                "           CONCAT(TIMESTAMPDIFF(WEEK, P.createdAt, now()), '주 전')\n" +
                "        when TIMESTAMPDIFF(DAY, P.createdAt, now()) >= 1 then\n" +
                "           CONCAT(TIMESTAMPDIFF(DAY, P.createdAt, now()), '일 전')\n" +
                "else DATE_FORMAT(P.createdAt, '%p %H : %i') end as Time\n" +
                "from 14_TEST.Like L\n" +
                "inner join 14_TEST.Product P on L.productId = P.productId\n" +
                "inner join 14_TEST.User U on P.userId = U.userId\n" +
                "inner join 14_TEST.ProductImage PI on P.productId = PI.productId\n" +
                "where L.userId = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetLikeRes(
                        rs.getInt("likeId"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("userNickName"),
                        rs.getString("ImageUrl"),
                        rs.getString("time")),
                getUserParams);
    }

    public int createLike(PostLikeReq postLikeReq) {
        String createLikeQuery = "insert into 14_TEST.Like(userId, productId) VALUES (?,?)";
        Object[] createLikeParams = new Object[]{postLikeReq.getUserId(), postLikeReq.getProductId()};
        this.jdbcTemplate.update(createLikeQuery, createLikeParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int modifyLikeStatus(PatchLikeStatusReq patchLikeStatusReq){
        String modifyLikeQuery = "update 14_TEST.Like set status =? where likeId = ? ";
        Object[] modifyLikeParams = new Object[]{patchLikeStatusReq.getStatus(), patchLikeStatusReq.getLikeId()};

        return this.jdbcTemplate.update(modifyLikeQuery,modifyLikeParams);
    }
}
