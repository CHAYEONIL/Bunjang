package com.example.demo.src.qna;

import com.example.demo.src.home.model.GetBannerRes;
import com.example.demo.src.qna.model.GetQnaRes;
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
}
