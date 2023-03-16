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
        String getUserNoticeQuery = "select noticeId, content, " +
        "case when TIMESTAMPDIFF(SECOND, N.updatedAt,CURRENT_TIMESTAMP)<60\n" +
        "                        then concat(TIMESTAMPDIFF(SECOND, N.updatedAt,CURRENT_TIMESTAMP),'초 전')\n" +
                "                        when TIMESTAMPDIFF(MINUTE , N.updatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(MINUTE , N.updatedAt,CURRENT_TIMESTAMP),'분 전')\n" +
                "                        when TIMESTAMPDIFF(HOUR , N.updatedAt,CURRENT_TIMESTAMP)<24\n" +
                "                        then concat(TIMESTAMPDIFF(HOUR , N.updatedAt,CURRENT_TIMESTAMP),'시간 전')\n" +
                "                        when TIMESTAMPDIFF(DAY , N.updatedAt,CURRENT_TIMESTAMP)<30\n" +
                "                        then concat(TIMESTAMPDIFF(DAY , N.updatedAt,CURRENT_TIMESTAMP),'일 전')\n" +
                "                        when TIMESTAMPDIFF(MONTH ,N.updatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "                        then concat(TIMESTAMPDIFF(MONTH ,N.updatedAt,CURRENT_TIMESTAMP), '달 전')\n" +
                "                        else concat(TIMESTAMPDIFF(YEAR,N.updatedAt,CURRENT_TIMESTAMP), '년 전')\n" +
                "                        end AS updatedAt\n" +
                " from `Notice` as N where userId = ? and status='ACTIVE'";
        return this.jdbcTemplate.query(getUserNoticeQuery,
                (rs,rowNum)->new GetNoticeRes(
                        rs.getInt("noticeId"),
                        rs.getString("content"),
                        rs.getString("updatedAt")),
                getUserParams);


    }
}
