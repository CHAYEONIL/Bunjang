package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("addresses")
public class AddressController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AddressProvider addressProvider;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final JwtService jwtService;

    public AddressController(AddressProvider addressProvider, AddressService addressService, JwtService jwtService) {
        this.addressProvider = addressProvider;
        this.addressService = addressService;
        this.jwtService = jwtService;
    }

    /**
     * 주소 등록 API
     * [POST] /addresses
     * @param postAddressReq
     * @return
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostAddressRes> createAddress(@RequestBody PostAddressReq postAddressReq) {

        // 주소 이름 유효성 검사
        if(postAddressReq.getName() == null) {
            return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS_NAME);
        }
        // 휴대폰번호 유효성 검사
        if(postAddressReq.getPhoneNum() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_PHONENUM);
        }
        if(!(postAddressReq.getPhoneNum().length() == 10 || postAddressReq.getPhoneNum().length() == 11)){
            return new BaseResponse<>(POST_ADDRESS_INVALID_PHONENUM);
        }
        // 주소 유효성 검사
        if(postAddressReq.getAddressName() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
        }
        // 상세 주소 유효성 검사
        if(postAddressReq.getAddressDetail() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESSDETAIL);
        }

        try {
            int userIdByJwt = jwtService.getUserIdx();
            PostAddressRes postAddressRes = addressService.createAddress(userIdByJwt, postAddressReq);
            return new BaseResponse<>(postAddressRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * 특정 회원 주소 전체 조회 API
     * @return
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAddressRes>> getAddresses(){
        try {
            int userIdByJwt = jwtService.getUserIdx();
            List<GetAddressRes> getAddressRes = addressProvider.getAddress(userIdByJwt);
            return new BaseResponse<>(getAddressRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 주소 수정 API
     * @param addressId
     * @param address
     * @return
     */
    @ResponseBody
    @PatchMapping("/{addressId}")
    public BaseResponse<String> modifyAddress(@PathVariable("addressId") int addressId, @RequestBody PatchAddressReq address) {

        try {
            // 주소 이름 유효성 검사
            if(address.getName() == null) {
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS_NAME);
            }
            // 휴대폰번호 유효성 검사
            if(address.getPhoneNum() == null){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_PHONENUM);
            }
            if(!(address.getPhoneNum().length() == 10 || address.getPhoneNum().length() == 11)){
                return new BaseResponse<>(POST_ADDRESS_INVALID_PHONENUM);
            }
            // 주소 유효성 검사
            if(address.getAddressName() == null){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
            }
            // 상세 주소 유효성 검사
            if(address.getAddressDetail() == null){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESSDETAIL);
            }

            int userIdByJwt = jwtService.getUserIdx();

            PatchAddressReq patchAddressReq = new PatchAddressReq(addressId, address.getName(), address.getPhoneNum(), address.getAddressName(), address.getAddressDetail(), address.getAddressBase());
            addressService.modifyAddress(addressId, patchAddressReq);

            String result = "주소가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 주소 삭제 API
     * @param addressId
     * @return
     */
    @ResponseBody
    @PatchMapping("/{addressId}/status")
    public BaseResponse<String> deleteAddress(@PathVariable("addressId") int addressId) {

        try {
            int userIdByJwt = jwtService.getUserIdx();
            addressService.deleteAddress(addressId);
            String result = "주소가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
