package com.example.demo.src.like;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.like.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final LikeProvider likeProvider;

    @Autowired
    private final LikeService likeService;

    @Autowired
    private final JwtService jwtService;

    public LikeController(LikeProvider likeProvider, LikeService likeService, JwtService jwtService){
        this.likeProvider = likeProvider;
        this.likeService = likeService;
        this.jwtService = jwtService;
    }

    /**
     * 찜 검색 API
     * [POST] /likes?userId={userId}
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetLikeRes>> getUserId(@RequestParam(value = "userId") int userId) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            List<GetLikeRes> getLikeRes = likeProvider.getUserId(userId);
            return new BaseResponse<>(getLikeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 찜추가 API
     * [POST] /likes
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostLikeRes> createLike(@RequestBody PostLikeReq postLikeReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            PostLikeRes postLikeRes = likeService.createLike(postLikeReq);
            return new BaseResponse<>(postLikeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜삭제 API
     * [PATCH] /likes/:likeId/status
     */
    @ResponseBody
    @PatchMapping("/{likeId}/status")
    public BaseResponse<String> modifyLikestatus(@PathVariable("likeId") int likeId, @RequestBody Like like){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            PatchLikeStatusReq patchLikeStatusReq = new PatchLikeStatusReq(likeId, like.getStatus());
            likeService.modifyLikeStatus(patchLikeStatusReq);

            String result = "수정이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
