package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("orders")
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    /**
     * 상품 주문 API
     * @param productId
     * @param postOrderReq
     * @return
     */
    @ResponseBody
    @PostMapping("{productId}")
    public BaseResponse<PostOrderRes> createOrder(@PathVariable("productId") int productId, @RequestBody PostOrderReq postOrderReq) {

        try {
            int userId = jwtService.getUserIdx();
            PostOrderRes postOrderRes = orderService.createOrder(userId, productId, postOrderReq);
            return new BaseResponse<>(postOrderRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 주문 상세 조회 API
     * @param orderId
     * @return
     */
    @ResponseBody
    @GetMapping("{orderId}")
    public BaseResponse<GetOrderRes> getOrder(@PathVariable("orderId") int orderId) {
        try {
            jwtService.getUserIdx();
            GetOrderRes getOrderRes = orderProvider.getOrder(orderId);
            return new BaseResponse<>(getOrderRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("{orderId}/status")
    public BaseResponse<String> deleteOrder(@PathVariable("orderId") int orderId) {
        try {
            orderService.deleteOrder(orderId);
            String result = "주문이 취소되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
