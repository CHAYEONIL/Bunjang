package com.example.demo.src.announcement;

import com.example.demo.src.announcement.model.GetAnnouncementRes;
import com.example.demo.src.announcement.model.GetAnnouncementsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.chrono.JapaneseDate;
import java.util.List;

@Repository
public class AnnouncementDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetAnnouncementsRes> getAnnouncements() {

        String getAnnounceQuery = "select announcementId, title, updatedAt, annoStatus from `Announcement` where annoStatus='Y'";
        return this.jdbcTemplate.query(getAnnounceQuery,
                (rs, rowNum)->new GetAnnouncementsRes(
                        rs.getInt("announcementId"),
                        rs.getString("title"),
                        rs.getString("updatedAt"),
                        rs.getString("annoStatus")));


    }

    public GetAnnouncementRes getAnnouncement(int announcementId) {

        int getAnnoParams = announcementId;

        String getAnnonceQuery = "select announcementId, title, content, updatedAt, annoStatus from `Announcement` where announcementId = ? and annoStatus='Y'";
        return this.jdbcTemplate.queryForObject(getAnnonceQuery,
                (rs, rowNum) -> new GetAnnouncementRes(
                        rs.getInt("announcementId"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("updatedAt"),
                        rs.getString("annoStatus")), getAnnoParams);

    }
}
