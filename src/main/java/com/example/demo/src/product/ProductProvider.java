package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.model.GetProductsDataRes;
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



    public List<GetProductsDataRes> getProductData(int productId) throws BaseException {

        if (checkProductExist(productId) == 0) {
            throw new BaseException(PRODUCT_NOT_EXIST);
        }
        try {
            List<GetProductsDataRes> getProductsDataRes = productDao.getProduct(productId);
            return getProductsDataRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }


    }
}
