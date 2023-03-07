package com.example.demo.src.home;

import com.example.demo.src.home.model.GetBannerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class HomeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 홈화면 배너
     * @param
     * @return
     */
    public List<GetBannerRes> getBanner() {
        String getBannerQuery = "select B.bannerId, B.bannerImageUrl from Banner B";
        return this.jdbcTemplate.query(getBannerQuery,
                (rs, rowNum) -> new GetBannerRes(
                        rs.getInt("bannerId"),
                        rs.getString("bannerImageUrl"))
                );

    }
}
