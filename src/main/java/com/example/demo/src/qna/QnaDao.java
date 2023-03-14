package com.example.demo.src.qna;

import com.example.demo.src.qna.model.GetQnaIdRes;
import com.example.demo.src.qna.model.GetQnaRes;
import com.example.demo.src.review.model.GetReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class QnaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetQnaRes> getQnas() {
        String getQnaQuery = "select questionId, question, answer, category from Qnas";
        return this.jdbcTemplate.query(getQnaQuery,
                (rs, rowNum) -> new GetQnaRes(
                        rs.getInt("questionId"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("category"))
        );
    }

    public GetQnaIdRes getQnaId(int questionId){
        String getQnaQuery = "select questionId, question, answer, category from Qnas where questionId = ?";
        int getQnaParams = questionId;
        return jdbcTemplate.queryForObject(getQnaQuery,
                (rs, rsNum) -> new GetQnaIdRes(
                        rs.getInt("questionId"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("category")),
                getQnaParams);
    }
    public List<GetQnaRes> getCategory(String category){
        String getQnaQuery = "select * from Qnas where category = ?";
        String getQnaParams = category;
        return this.jdbcTemplate.query(getQnaQuery,
                (rs,rowNum) -> new GetQnaRes(
                        rs.getInt("questionId"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("category")),
                getQnaParams);
    }
}
