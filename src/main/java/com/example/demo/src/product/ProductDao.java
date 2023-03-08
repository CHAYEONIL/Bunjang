package com.example.demo.src.product;

import com.example.demo.src.product.model.GetProductsDataRes;
import com.example.demo.src.product.model.GetProductsImgRes;
import com.example.demo.src.product.model.GetProductsLikeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class ProductDao {

    private List<GetProductsImgRes> getProductsImgRes;
    private List<GetProductsLikeRes> getProductsLikeRes;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int checkProductExist(int productId) {

        String checkProductExistQuery = "select exists(select productId from Product where productId = ? and status='ACTIVE')";
        int checkProductExistParams = productId;
        return this.jdbcTemplate.queryForObject(checkProductExistQuery, int.class, checkProductExistParams);

    }

    /**
     * 상품 상세 조회
     * @param productId
     * @return
     */
    public List<GetProductsDataRes> getProduct(int productId) {
        String getProductQuery = "select P.productId, P.userId, P.title, P.category, IFNULL(location, '지역정보 없음'), P.productStatus, P.isChangable, P.quantity, P.price, P.isFreeShip, P.contents, P.isSagePay, P.tradeStatus, P.updatedAt,\n" +
                "                        case when TIMESTAMPDIFF(SECOND, P.updatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(SECOND, P.updatedAt,CURRENT_TIMESTAMP),'초 전')\n" +
                "                        when TIMESTAMPDIFF(MINUTE , P.updatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(MINUTE , P.updatedAt,CURRENT_TIMESTAMP),'분 전')\n" +
                "                        when TIMESTAMPDIFF(HOUR , P.updatedAt,CURRENT_TIMESTAMP)<24\n" +
                "                        then concat(TIMESTAMPDIFF(HOUR , P.updatedAt,CURRENT_TIMESTAMP),'시간 전')\n" +
                "                        when TIMESTAMPDIFF(DAY , P.updatedAt,CURRENT_TIMESTAMP)<30\n" +
                "                        then concat(TIMESTAMPDIFF(DAY , P.updatedAt,CURRENT_TIMESTAMP),'일 전')\n" +
                "                        when TIMESTAMPDIFF(MONTH ,P.updatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "                        then concat(TIMESTAMPDIFF(MONTH ,P.updatedAt,CURRENT_TIMESTAMP), '달 전')\n" +
                "                        else concat(TIMESTAMPDIFF(YEAR,P.updatedAt,CURRENT_TIMESTAMP), '년 전')\n" +
                "                        end AS productUpdatedAtTime,\n" +
                "                case when location is null then '지역정보 없음'\n" +
                "                        else location end locationnull\n" +
                "                       from Product P where P.productId=? and P.status='ACTIVE'";
        int getProductParams = productId;

        String getProductLikeQuery = "select count(*) as likes from `Like` where productId=?";
        String getProductImgQuery = "select PI.productId, PI.imageUrl from ProductImage PI where productId=?";

        return this.jdbcTemplate.query(getProductQuery,
                (rs, rowNum) -> new GetProductsDataRes(
                        rs.getInt("productId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("locationnull"),
                        rs.getString("productStatus"),
                        rs.getString("isChangable"),
                        rs.getInt("quantity"),
                        rs.getString("price"),
                        rs.getString("isFreeShip"),
                        rs.getString("contents"),
                        rs.getString("isSagePay"),
                        rs.getString("tradeStatus"),
                        rs.getString("productUpdatedAtTime"),
                        getProductsLikeRes = this.jdbcTemplate.query(getProductLikeQuery,
                                (rl, rowNum2)->new GetProductsLikeRes(
                                        rl.getInt("likes")), rs.getInt("productId")),
                        getProductsImgRes = this.jdbcTemplate.query(getProductImgQuery,
                                (rk, rowNum3)->new GetProductsImgRes(
                                        rk.getInt("productId"),
                                        rk.getString("imageUrl")),
                                rs.getInt("productId"))), getProductParams);

    }
}
