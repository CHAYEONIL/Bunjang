package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;
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
    /**
     * 상점 리뷰 검색 API
     * [POST] /reviews
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetReviewRes>> getStoreUserId(@RequestParam(value = "storeUserId") int storeUserId) {
        try{
            List<GetReviewRes> getReviewRes = reviewProvider.getStoreUserId(storeUserId);
            return new BaseResponse<>(getReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 리뷰수정 API
     * [POST] /reviews/:reviewId
     */
    @ResponseBody
    @PatchMapping("/{reviewId}")
    public BaseResponse<String> modifyReview(@PathVariable("reviewId") int reviewId, @RequestBody Review review){
        try {
            //같다면 유저네임 변경
            PatchReviewReq patchReviewReq = new PatchReviewReq(reviewId, review.getScore(), review.getContent());
            reviewService.modifyReview(patchReviewReq);

            String result = "수정이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 리뷰삭제 API
     * [POST] /reviews/:reviewId/status
     */
    @ResponseBody
    @PatchMapping("/{reviewId}/status")
    public BaseResponse<String> modifyReviewstatus(@PathVariable("reviewId") int reviewId, @RequestBody Review review){
        try {
            //같다면 유저네임 변경
            PatchReviewStatusReq patchReviewStatusReq = new PatchReviewStatusReq(reviewId, review.getStatus());
            reviewService.modifyReviewStatus(patchReviewStatusReq);

            String result = "수정이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
