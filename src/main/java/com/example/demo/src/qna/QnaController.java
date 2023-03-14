package com.example.demo.src.qna;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.qna.model.GetQnaRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetQnaRes>> getBanner() {

        try {
            List<GetQnaRes> getQnaRes = qnaProvider.getQna();
            return new BaseResponse<>(getQnaRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
