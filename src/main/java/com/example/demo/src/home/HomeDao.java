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
                "select P.productId, P.price, P.title, P.isSagePay, P.tradeStatus, P.status, (select PI.imageUrl from ProductImage PI where PI.status = 'ACTIVE' and P.productId = PI.productId limit 1) as imageUrl \n" +
                        "from Product P\n" +
                        "where P.tradeStatus = 'Available' and P.status = 'ACTIVE'";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductsRes(
                        rs.getInt("productId"),
                        rs.getString("price"),
                        rs.getString("title"),
                        rs.getString("isSagePay"),
                        rs.getString("tradeStatus"),
                        rs.getString("status"),
                        rs.getString("imageUrl")
                ));

    }
}
