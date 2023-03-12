package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_ID;

@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    private final ReviewProdiver reviewProdiver;
    private final JwtService jwtService;

    public ReviewService(ReviewDao reviewDao, ReviewProdiver reviewProdiver, JwtService jwtService){
        this.reviewDao = reviewDao;
        this.reviewProdiver = reviewProdiver;
        this.jwtService = jwtService;
    }
    public PostReviewRes createReview(PostReviewReq postReviewReq) throws BaseException{
        try{
            int reviewId = reviewDao.createReview(postReviewReq);
            return new PostReviewRes(reviewId);
        } catch (Exception exception) {
            logger.error("App - createReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void modifyReview(PatchReviewReq patchReviewReq) throws BaseException {
        try {
            int result = reviewDao.modifyReview(patchReviewReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_ID);
            }
        } catch (Exception exception) {
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
