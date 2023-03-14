package com.example.demo.src.notice;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.notice.model.GetNoticeRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final NoticeProvider noticeProvider;
    @Autowired
    private final NoticeService noticeService;
    @Autowired
    private final JwtService jwtService;




    /**
     * 알림 전체 조회
     * @return
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetNoticeRes>> getNotices() {
        try {

            int userId = jwtService.getUserIdx();

            List<GetNoticeRes> getNoticeRes = noticeProvider.getNotices(userId);
            return new BaseResponse<>(getNoticeRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
