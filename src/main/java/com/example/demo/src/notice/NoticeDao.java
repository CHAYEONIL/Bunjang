package com.example.demo.src.notice;

import com.example.demo.src.notice.model.GetNoticeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class NoticeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetNoticeRes> getNotices(int userId) {
        int getUserParams = userId;
        String getUserNoticeQuery = "select noticeId, content, updatedAt from `Notice` where userId = ? and status='ACTIVE'";
        return this.jdbcTemplate.query(getUserNoticeQuery,
                (rs,rowNum)->new GetNoticeRes(
                        rs.getInt("noticeId"),
                        rs.getString("content"),
                        rs.getString("updatedAt")),
                getUserParams);


    }
}
