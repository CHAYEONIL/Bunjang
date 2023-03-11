package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final ProductProvider productProvider;
    private final JwtService jwtService;

    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider, JwtService jwtService) {
        this.productDao = productDao;
        this.productProvider = productProvider;
        this.jwtService = jwtService;
    }


    //POST
    public PostProductRes createProduct(int userId, PostProductReq postProductReq) throws BaseException {

        try {
            int productId = productDao.createProduct(userId, postProductReq);
            for(int i = 0; i < postProductReq.getProductImages().size(); i++) {
                productDao.createProductImage(productId, postProductReq.getProductImages().get(i));
            }
            return new PostProductRes(productId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
