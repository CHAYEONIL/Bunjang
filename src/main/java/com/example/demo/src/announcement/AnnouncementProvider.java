package com.example.demo.src.announcement;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.announcement.model.GetAnnouncementRes;
import com.example.demo.src.announcement.model.GetAnnouncementsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class AnnouncementProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AnnouncementDao announcementDao;

    @Autowired
    public AnnouncementProvider(AnnouncementDao announcementDao) {
        this.announcementDao = announcementDao;
    }

    public List<GetAnnouncementsRes> getAnnouncements() throws BaseException {
        try {
            List<GetAnnouncementsRes> getAnnouncementsRes = announcementDao.getAnnouncements();
            return getAnnouncementsRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 특정 공지사항 조회
     * @param announcementId
     * @return
     */
    public GetAnnouncementRes getAnnouncement(int announcementId) throws BaseException {

        try {
            GetAnnouncementRes getAnnouncementRes = announcementDao.getAnnouncement(announcementId);
            return getAnnouncementRes;
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
