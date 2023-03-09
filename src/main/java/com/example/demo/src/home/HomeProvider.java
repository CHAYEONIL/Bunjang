package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.home.model.GetBannerRes;
import com.example.demo.src.home.model.GetProductsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeProvider {

    private final HomeDao homeDao;
    private JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HomeProvider(HomeDao homeDao) {
        this.homeDao = homeDao;
    }

    /**
     * 배너 조회
     * @param
     * @return
     * @throws BaseException
     */
    public List<GetBannerRes> getBanner() throws BaseException {
        try {
            List<GetBannerRes> getBannerRes = homeDao.getBanner();
            return getBannerRes;
        } catch(Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetProductsRes> getHomeProducts() throws BaseException {
        try {
            List<GetProductsRes> getProductsRes = homeDao.getHomeProducts();
            return getProductsRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
