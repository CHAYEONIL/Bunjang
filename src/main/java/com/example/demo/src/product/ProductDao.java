package com.example.demo.src.product;

import com.example.demo.src.product.model.*;
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
    public GetProductsDataRes getProduct(int productId) {
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

        return this.jdbcTemplate.queryForObject(getProductQuery,
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

    public GetUserDataRes getUserData(int userId) {

        int getUserParams = userId;
        String getUserDataQuery = "select ROUND(AVG(R.score), 1) as score, U.userNickName, U.profileImageUrl\n" +
                "from User U \n" +
                "inner join Product P on U.userId = P.userId\n" +
                "left join Review R on U.userId = R.purchaseUserId\n" +
                "where P.userId=?";

        return this.jdbcTemplate.queryForObject(getUserDataQuery,
                (rs, rowNum) -> new GetUserDataRes(
                        rs.getDouble("score"),
                        rs.getString("profileImageUrl"),
                        rs.getString("userNickName")),getUserParams

                );
    }


    public List<GetUserProductsRes> getUserProducts(int userId) {
        String getUserQuery = "select *,\n" +
                "(select COUNT(*) from `Like` L where L.userId = P.userId and L.productId = P.productId) as productLike\n" +
                "from Product P\n" +
                "left join User U on P.userId = U.userId \n" +
                "left join `Like` L on P.productId = L.productId and U.userId = L.userId \n" +
                "where P.userId=? and P.status='ACTIVE'";
        int getUserParams = userId;
        String getProductImgQuery = "select * from ProductImage PI left join Product P on P.productId = PI.productId where P.productId=? and P.status='ACTIVE'";

        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum)->new GetUserProductsRes(
                        rs.getInt("productId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getString("price"),
                        rs.getInt("productLike"),
                        rs.getInt("likeId"),
                        getProductsImgRes = this.jdbcTemplate.query(getProductImgQuery,
                                (rk, rowNum1) -> new GetProductsImgRes(
                                        rk.getInt("productId"),
                                        rk.getString("imageUrl")),
                                rs.getInt("productId"))), getUserParams
                                );


    }

    public List<GetUserReviewRes> getUserReview(int userId) {
        int getUserParams = userId;
        String getUserReviewQuery = "select *, \n" +
                "                        case when TIMESTAMPDIFF(SECOND, R.updatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(SECOND, R.updatedAt,CURRENT_TIMESTAMP),'초 전')\n" +
                "                        when TIMESTAMPDIFF(MINUTE , R.updatedAt,CURRENT_TIMESTAMP)<60\n" +
                "                        then concat(TIMESTAMPDIFF(MINUTE , R.updatedAt,CURRENT_TIMESTAMP),'분 전')\n" +
                "                        when TIMESTAMPDIFF(HOUR , R.updatedAt,CURRENT_TIMESTAMP)<24\n" +
                "                        then concat(TIMESTAMPDIFF(HOUR , R.updatedAt,CURRENT_TIMESTAMP),'시간 전')\n" +
                "                        when TIMESTAMPDIFF(DAY , R.updatedAt,CURRENT_TIMESTAMP)<30\n" +
                "                        then concat(TIMESTAMPDIFF(DAY , R.updatedAt,CURRENT_TIMESTAMP),'일 전')\n" +
                "                        when TIMESTAMPDIFF(MONTH ,R.updatedAt,CURRENT_TIMESTAMP) < 12\n" +
                "                        then concat(TIMESTAMPDIFF(MONTH ,R.updatedAt,CURRENT_TIMESTAMP), '달 전')\n" +
                "                        else concat(TIMESTAMPDIFF(YEAR,R.updatedAt,CURRENT_TIMESTAMP), '년 전')\n" +
                "                        end AS reviewUpdatedAtTime\n" +
                "from Review R inner join User U on R.purchaseUserId = U.userId\n" +
                "inner join Product P on U.userId = P.userId\n" +
                "where storeUserId=? group by reviewId";

        return this.jdbcTemplate.query(getUserReviewQuery,
                (rm, rowNum) -> new GetUserReviewRes(
                        rm.getInt("productId"),
                        rm.getInt("purchaseUserId"),
                        rm.getString("content"),
                        rm.getDouble("score"),
                        rm.getString("createdAt"),
                        rm.getString("reviewUpdatedAtTime"))
                ,getUserParams);
    }

    /**
     * 상품 생성
     * @param userId
     * @param postProductReq
     * @return
     */
    public int createProduct(int userId, PostProductReq postProductReq) {
        String createProductQuery = "insert into Product (" +
                "userId, " +
                "title, " +
                "category, " +
                "location, " +
                "productStatus, " +
                "isChangable, " +
                "quantity, " +
                "price, " +
                "isFreeShip, " +
                "contents, " +
                "isSagePay) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createdProductParams = new Object[] {userId, postProductReq.getTitle(), postProductReq.getCategory(), postProductReq.getLocation(), postProductReq.getProductStatus(), postProductReq.getIsChangable(), postProductReq.getQuantity(), postProductReq.getPrice(), postProductReq.getIsFreeShip(), postProductReq.getContents(), postProductReq.getIsSagePay()};


        this.jdbcTemplate.update(createProductQuery, createdProductParams);
        String lastInsertIdQuery  = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

    }

    public int createProductImage(int productId, PostProductImageReq postProductImageReq) {
        String createProductImageQuery = "insert into ProductImage (productId, imageUrl) VALUES (?,?)";
        Object[] createProductImageParams = new Object[]{productId, postProductImageReq.getImageUrl()};

        this.jdbcTemplate.update(createProductImageQuery, createProductImageParams);
        String lastInsertIdQuery  = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

    }

    /**
     * 상품 수정
     * @param patchProductReq
     * @param productId
     * @return
     */
    public int modifyProduct(PatchProductReq patchProductReq, int productId) {
        String updateProductQuery = "UPDATE Product\n" +
                "SET title = ?, category = ?, location = ?, productStatus = ?, isChangable = ?, quantity = ?, price = ?, isFreeShip = ?, contents = ?, isSagePay = ?\n" +
                "WHERE productId = ?";
        Object[] updateProductParams = new Object[]{patchProductReq.getTitle(), patchProductReq.getCategory(), patchProductReq.getLocation(), patchProductReq.getProductStatus(), patchProductReq.getIsChangable(), patchProductReq.getQuantity(), patchProductReq.getPrice(), patchProductReq.getIsFreeShip(), patchProductReq.getContents(), patchProductReq.getIsSagePay(), productId};
        return this.jdbcTemplate.update(updateProductQuery, updateProductParams);
    }


}
