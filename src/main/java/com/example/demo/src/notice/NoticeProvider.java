package com.example.demo.src.notice;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.notice.model.GetNoticeRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class NoticeProvider {

    private final NoticeDao noticeDao;

    public NoticeProvider(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    public List<GetNoticeRes> getNotices(int userId) throws BaseException {
        try {
            List<GetNoticeRes> getNoticeRes = noticeDao.getNotices(userId);
            return getNoticeRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
