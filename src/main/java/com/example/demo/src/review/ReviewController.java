package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.POST_REVIEW_EMPTY_CONTENT;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProdiver reviewProvider;

    @Autowired
    private final ReviewService reviewService;

    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProdiver reviewProdiver, ReviewService reviewService, JwtService jwtService){
        this.reviewProvider = reviewProdiver;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 리뷰등록 API
     * [POST] /reviews
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostReviewRes> createUser(@RequestBody PostReviewReq postReviewReq) {
        if(postReviewReq.getContent()==null){
            return new BaseResponse<>(POST_REVIEW_EMPTY_CONTENT);
        }
        try{
            PostReviewRes postReviewRes = reviewService.createReview(postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}