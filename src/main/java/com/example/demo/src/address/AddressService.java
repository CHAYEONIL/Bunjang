package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class AddressService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;

    @Autowired
    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    /**
     * 주소 등록
     * @param userId
     * @param postAddressReq
     * @return
     * @throws BaseException
     */
    public PostAddressRes createAddress(int userId, PostAddressReq postAddressReq) throws BaseException {
        try {
            int addressId = addressDao.createAddress(userId, postAddressReq);
            return new PostAddressRes(addressId);
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 주소 수정
     * @param addressId
     * @param patchAddressReq
     * @throws BaseException
     */
    public void modifyAddress(int addressId, PatchAddressReq patchAddressReq) throws BaseException {
        try {
            int result = addressDao.modifyAddress(addressId, patchAddressReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_ADDRESS);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 주소 삭제 API
     * @param addressId
     * @throws BaseException
     */
    public void deleteAddress(int addressId) throws BaseException{
        try {
            int result = addressDao.deleteAddress(addressId);
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
