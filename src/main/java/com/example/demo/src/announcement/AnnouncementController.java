package com.example.demo.src.announcement;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.announcement.model.GetAnnouncementRes;
import com.example.demo.src.announcement.model.GetAnnouncementsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AnnouncementProvider announcementProvider;
    @Autowired
    private final AnnouncementService announcementService;
    @Autowired
    private final JwtService jwtService;

    public AnnouncementController(AnnouncementProvider announcementProvider, AnnouncementService announcementService, JwtService jwtService) {
        this.announcementProvider = announcementProvider;
        this.announcementService = announcementService;
        this.jwtService = jwtService;
    }

    /**
     * 공지사항 전체 조회
     * @return
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAnnouncementsRes>> getAnnouncements() {
        try {
            int userIdByJwt = jwtService.getUserIdx();
            
            List<GetAnnouncementsRes> getAnnouncementsRes = announcementProvider.getAnnouncements();
            return new BaseResponse<>(getAnnouncementsRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("{announcementId}")
    public BaseResponse<GetAnnouncementRes> getAnnouncement(@PathVariable("announcementId") int announcementId) {

        try {
            int userIdByJwt = jwtService.getUserIdx();
            
            GetAnnouncementRes getAnnouncementRes = announcementProvider.getAnnouncement(announcementId);
            return new BaseResponse<>(getAnnouncementRes);
        } catch (BaseException e) {
            System.out.println(e);
            return new BaseResponse<>(e.getStatus());
        }
    }


}

