package com.example.demo.src.order;

import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 주문 생성
     * @param userId
     * @param productId
     * @param postOrderReq
     * @return
     */
    public int createOrder(int userId, int productId, PostOrderReq postOrderReq) {
        String createOrderQuery = "insert into `Order` (userId, productId, addressId, deliveryReq, price, deliveryFee, totalPrice) VALUES (?,?,?,?,?,?,?)";
        Object[] createOrderParams = new Object[]{userId, productId, postOrderReq.getAddressId(), postOrderReq.getDeliveryReq(), postOrderReq.getPrice(), postOrderReq.getDeliveryFee(), postOrderReq.getTotalPrice()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    /**
     * 주문 상세 조회
     * @param orderId
     * @return
     */
    public GetOrderRes getOrder(int orderId) {
        String getOrderQuery = "select P.productId, P.price as productPrice, P.title as productTitle, SU.userNickName as sellerName " +
                "     , REPLACE(SUBSTRING(O.createdAt, 1, 19), '-', '/') as orderTime\n" +
                "     , O.status as orderStatus, O.totalPrice as orderTotalPrice, O.price as orderProductPrice, O.deliveryFee\n" +
                "     , A.addressId, U.userNickName as buyerName, A.phoneNum, A.name as addressName\n" +
                "     , CONCAT(A.addressName, ' ', A.addressDetail) as addressDetail, O.deliveryReq as orderDeliveryReq\n" +
                "from `Order` O\n" +
                "inner join User U on O.userId = U.userId\n" +
                "inner join Product P on O.productId = P.productId\n" +
                "inner join Address A on O.addressId = A.addressId\n" +
                "inner join User SU on P.userId = SU.userId\n" +
                "where orderId = ?";
        int getOrderParams = orderId;
        return this.jdbcTemplate.queryForObject(getOrderQuery,
                (rs, rowNum) -> new GetOrderRes(
                        rs.getInt("productId"),
                        rs.getString("productPrice"),
                        rs.getString("productTitle"),
                        rs.getString("sellerName"),
                        rs.getString("orderTime"),
                        rs.getString("orderStatus"),
                        rs.getInt("orderTotalPrice"),
                        rs.getInt("orderProductPrice"),
                        rs.getInt("deliveryFee"),
                        rs.getInt("addressId"),
                        rs.getString("buyerName"),
                        rs.getString("phoneNum"),
                        rs.getString("addressName"),
                        rs.getString("addressDetail"),
                        rs.getString("orderDeliveryReq")),
                getOrderParams);

    }

    /**
     * 주문 취소
     * @param orderId
     * @return
     */
    public int deleteOrder(int orderId) {
        String deleteOrderQuery = "UPDATE `Order` SET status = 'DELETED' WHERE orderId = ?";
        int deleteOrderParams = orderId;
        return this.jdbcTemplate.update(deleteOrderQuery, deleteOrderParams);
    }
}
