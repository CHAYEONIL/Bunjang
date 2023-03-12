package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;

    /**
     * [GET] 상품 상세 페이지 - 상품 정보 API
     * @param productId
     * @return
     */
    @ResponseBody
    @GetMapping("/{productId}/detail")
    public BaseResponse<GetProductsDataRes> getProducts(@PathVariable("productId") int productId) {

        try{
            GetProductsDataRes getProductsDataRes = productProvider.getProductData(productId);
            return new BaseResponse<>(getProductsDataRes);
        } catch (BaseException e) {
            System.out.println(e);
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * [GET] /products/:productId/storeinfo
     */
    @ResponseBody
    @GetMapping("/{productId}/storeinfo")
    public BaseResponse<GetProductRes> getUsers(@PathVariable("productId") int productId) {
        try {
            GetProductRes getProductRes = productProvider.getProductUserData(productId);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException e) {
            System.out.println(e);
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * 상품 등록 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq) {

        try {
            int userId = jwtService.getUserIdx();

            PostProductRes postProductRes = productService.createProduct(userId, postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * 상품 수정 API
     * @param productId
     * @param patchProductReq
     * @return
     */
    @ResponseBody
    @PatchMapping("/{productId}")
    public BaseResponse<String> modifyProduct(@PathVariable("productId") int productId, @RequestBody PatchProductReq patchProductReq) {
        try {
            int userIdxJwt = jwtService.getUserIdx();
            productService.modifyProduct(userIdxJwt, productId, patchProductReq);
            String result = "상품이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            System.out.println(e);
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 상품 삭제 API
     * @param productId
     * @return
     */
    @ResponseBody
    @PatchMapping("/{productId}/status")
    public BaseResponse<String> deleteProduct(@PathVariable("productId") int productId) {
        try {
            productService.deleteProduct(productId);
            String result = "상품이 삭제되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }



}
