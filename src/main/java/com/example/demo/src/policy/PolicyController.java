package com.example.demo.src.policy;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.policy.model.GetPolicyRes;
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
@RequestMapping("/policys")
public class PolicyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PolicyProvider policyProvider;

    @Autowired
    private final JwtService jwtService;

    public PolicyController(PolicyProvider policyProvider, JwtService jwtService) {
        this.policyProvider = policyProvider;
        this.jwtService = jwtService;
    }

    /**
     * 운영 정책 전체 조회 API
     * [GET] /policys
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPolicyRes>> getPolicy() {
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            List<GetPolicyRes> getPolicyRes = policyProvider.getPolicy();
            return new BaseResponse<>(getPolicyRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
