package com.example.demo.src.home;

import com.example.demo.src.home.model.GetBannerRes;
import com.example.demo.src.home.model.GetProductImgRes;
import com.example.demo.src.home.model.GetProductsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class HomeDao {

    private JdbcTemplate jdbcTemplate;

    private List<GetProductImgRes> getProductImgRes;

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

    /**
     * 홈 화면 상품
     */
    public List<GetProductsRes> getHomeProducts() {
        String getProductsQuery =
                "select P.productId, P.price, P.title, P.isSagePay, P.tradeStatus, P.status\n" +
                        "from Product P\n" +
                        "left join ProductImage PI on P.productId = PI.productId\n" +
                        "where P.tradeStatus = 'Available' and P.status = 'Y' and PI.status = 'Y'";

        String getProductImgQuery = "select PI.productImgUrlId, PI.imageUrl " +
                "from ProductImage PI " +
                "inner join Product P on PI.productId = P.productId where P.productId=? and PI.status='Y' ";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductsRes(
                        rs.getInt("productId"),
                        rs.getString("price"),
                        rs.getString("title"),
                        rs.getString("isSagePay"),
                        rs.getString("tradeStatus"),
                        rs.getString("status"),
                        getProductImgRes = this.jdbcTemplate.query(getProductImgQuery,
                                (rk, rownum)->new GetProductImgRes(rk.getInt("productImgUrlId"),
                                        rk.getString("imageUrl")), rs.getInt("productId")
                        )));

    }
}
