package com.example.demo.src.policy;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.policy.model.GetPolicyRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyProvider {
    private final PolicyDao policyDao;
    private JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PolicyProvider(PolicyDao policyDao, JwtService jwtService) {
        this.policyDao = policyDao;
        this.jwtService = jwtService;
    }
    public List<GetPolicyRes> getPolicy() throws BaseException {
        try {
            List<GetPolicyRes> getPolicyRes = policyDao.getPolicy();
            return getPolicyRes;
        } catch(Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
