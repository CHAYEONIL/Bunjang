package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class OrderService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public PostOrderRes createOrder(int userId, int productId, PostOrderReq postOrderReq) throws BaseException {
        try {
            int orderId = orderDao.createOrder(userId, productId, postOrderReq);
            return new PostOrderRes(orderId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 주문 취소
     * @param orderId
     * @throws BaseException
     */
    public void deleteOrder(int orderId) throws BaseException {
        try {
            int result = orderDao.deleteOrder(orderId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
