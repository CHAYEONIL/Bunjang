package com.example.demo.src.qna;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.qna.model.GetQnaIdRes;
import com.example.demo.src.qna.model.GetQnaRes;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qnas")
public class QnaController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final QnaProvider qnaProvider;

    @Autowired
    private final JwtService jwtService;

    public QnaController(QnaProvider qnaProvider, JwtService jwtService) {
        this.qnaProvider = qnaProvider;
        this.jwtService = jwtService;
    }
    /**
     * 자주 묻는 질문 전체 조회 API
     * [GET] /qnas
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetQnaRes>> getQna() {
        try {
            List<GetQnaRes> getQnaRes = qnaProvider.getQna();
            return new BaseResponse<>(getQnaRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /**
     * 자주 묻는 질문 특정 조회 API
     * [GET] /qnas/:questionId
     */
    @ResponseBody
    @GetMapping("{questionId}")
    public BaseResponse<GetQnaIdRes> getQnaId(@PathVariable("questionId") int questionId) {
        try {
            GetQnaIdRes getQnaIdRes = qnaProvider.getQnaId(questionId);
            return new BaseResponse<>(getQnaIdRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 카테고리 검색 API
     * [GET] /qnas?category={category}
     */
    @ResponseBody
    @GetMapping("category")
    public BaseResponse<List<GetQnaRes>> getCategory(@RequestParam(value = "category") String category) {
        try{
            List<GetQnaRes> getQnaRes = qnaProvider.getCategory(category);
            return new BaseResponse<>(getQnaRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
