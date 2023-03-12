package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.PRODUCT_NOT_EXIST;

@Service
public class ProductProvider {

    private final ProductDao productDao;
    private JwtService jwtService;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductDao productDao, JwtService jwtService) {
        this.productDao = productDao;
        this.jwtService = jwtService;
    }

    // 상품 상태 확인
    public int checkProductExist(int productId) throws BaseException {
        try {
            return productDao.checkProductExist(productId);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    /**
     * 상품 상세 페이지 - 상품 정보 API
     * @param productId
     * @return
     * @throws BaseException
     */
    public GetProductsDataRes getProductData(int productId) throws BaseException {

        if (checkProductExist(productId) == 0) {
            throw new BaseException(PRODUCT_NOT_EXIST);
        }
        try {
            GetProductsDataRes getProductsDataRes = productDao.getProduct(productId);
            return getProductsDataRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }


    }

    public GetProductRes getProductUserData(int productId) throws BaseException {
        if (checkProductExist(productId) == 0) {
            throw new BaseException(PRODUCT_NOT_EXIST);
        }
        try {
            GetProductsDataRes getProductsDataRes = productDao.getProduct(productId);
            // 판매 상점 정보
            GetUserDataRes getUserDataRes = productDao.getUserData(getProductsDataRes.getUserId());
            // 판메 상점의 다른 상품
            List<GetUserProductsRes> getUserProductsRes = productDao.getUserProducts(getProductsDataRes.getUserId());
            //판매 상점 리뷰
            List<GetUserReviewRes> getUserReviewRes = productDao.getUserReview(getProductsDataRes.getUserId());

            GetProductRes getProductRes = new GetProductRes(getUserDataRes, getUserProductsRes, getUserReviewRes);
            return getProductRes;

        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 상품 검색
     * @param title
     * @return
     * @throws BaseException
     */
    public List<GetProductSearchRes> getSearchProducts(String title) throws BaseException {
        try {
            List<GetProductSearchRes> getProductSearchRes = productDao.getSearchProducts(title);
            return getProductSearchRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 상품 카테고리별 검색
     * @param category
     * @return
     * @throws BaseException
     */
    public List<GetProductSearchRes> getSearchCateProducts(String category) throws BaseException {
        try {
            List<GetProductSearchRes> getProductSearchRes = productDao.getSearchCateProducts(category);
            return getProductSearchRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
