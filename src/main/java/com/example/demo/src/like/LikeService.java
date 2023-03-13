package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.src.like.model.PostLikeReq;
import com.example.demo.src.like.model.PostLikeRes;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class LikeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LikeDao likeDao;
    private final LikeProvider likeProvider;
    private final JwtService jwtService;

    public LikeService(LikeDao likeDao, LikeProvider likeProvider, JwtService jwtService){
        this.likeDao = likeDao;
        this.likeProvider = likeProvider;
        this.jwtService = jwtService;
    }
    public PostLikeRes createLike(PostLikeReq postLikeReq) throws BaseException {
        try{
            int likeId = likeDao.createLike(postLikeReq);
            return new PostLikeRes(likeId);
        } catch (Exception exception) {
            logger.error("App - createReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

