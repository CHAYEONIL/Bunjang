package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        // 비밀번호 유효성 검사
        if(postUserReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }

        // 닉네임 유효성 검사
        if(postUserReq.getUserNickName() == null || postUserReq.getUserNickName().length() < 2){
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // 이메일 유효성 검사
            if(postLoginReq.getEmail() == null){ // null 값인 경우
                return new BaseResponse<>(POST_LOGIN_EMPTY_EMAIL);
            }
            if(!isRegexEmail(postLoginReq.getEmail())){ // 이메일 정규 표현
                return new BaseResponse<>(POST_LOGIN_INVALID_EMAIL);
            }

            // 비밀번호 유효성 검사
            if(postLoginReq.getPassword() == null){ // null 값인 경우
                return new BaseResponse<>(POST_LOGIN_EMPTY_PASSWORD);
            }

            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 상점수정 API
     * [POST] /users/:userId
     */
    @ResponseBody
    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUserName(@PathVariable("userId") int userId, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
            return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userId,user.getProfileImageUrl(),user.getUserNickName(), user.getContent());
            userService.modifyUserInfo(patchUserReq);

            String result = "수정이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 회원탈퇴 API
     * [POST] /users/:userId/status
     */
    @ResponseBody
    @PatchMapping("/{userId}/status")
    public BaseResponse<String> modifyStatus(@PathVariable("userId") int userId, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserStatusReq patchUserStatusReq = new PatchUserStatusReq(userId,user.getStatus());
            userService.modifyUserStatus(patchUserStatusReq);

            String result = "수정이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 마이페이지 API
     * [GET] /users/:userId
     * @return BaseResponse<GetMyPageRes>
     */
    @ResponseBody
    @GetMapping("{userId}")
    public BaseResponse<GetMyPageRes> getMyPage(@PathVariable("userId") int userId) {
        try {
            GetMyPageRes getMyPageRes = userProvider.getMyPage(userId);
            return new BaseResponse<>(getMyPageRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
