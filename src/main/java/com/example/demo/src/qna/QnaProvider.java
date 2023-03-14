package com.example.demo.src.qna;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.qna.model.GetQnaRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaProvider {
    private final QnaDao qnaDao;
    private JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public QnaProvider(QnaDao qnaDao, JwtService jwtService) {
        this.qnaDao = qnaDao;
        this.jwtService = jwtService;
    }
    public List<GetQnaRes> getQna() throws BaseException {
        try {
            List<GetQnaRes> getQnaRes = qnaDao.getQnas();
            return getQnaRes;
        } catch(Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
