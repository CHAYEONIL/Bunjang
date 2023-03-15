package com.example.demo.src.address;

import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.address.model.PostAddressReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 주소 등록
     * @param userId
     * @param postAddressReq
     * @return
     */
    public int createAddress(int userId, PostAddressReq postAddressReq) {
        String createAddressQuery = "insert into Address (userId, name, phoneNum, addressName, addressDetail, addressBase) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createAddressParams = new Object[]{userId, postAddressReq.getName(), postAddressReq.getPhoneNum(), postAddressReq.getAddressName(), postAddressReq.getAddressDetail(), postAddressReq.getAddressBase()};
        this.jdbcTemplate.update(createAddressQuery, createAddressParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public List<GetAddressRes> getAddress(int userId) {
        String getAddressQuery = "select addressId, name, phoneNum, addressName, addressDetail, addressBase\n" +
                "from Address\n" +
                "where userId = ? and status = 'active'";
        int getAddressParams = userId;
        return this.jdbcTemplate.query(getAddressQuery,
                (rs, rowNum) -> new GetAddressRes(
                        rs.getInt("addressId"),
                        rs.getString("name"),
                        rs.getString("phoneNum"),
                        rs.getString("addressName"),
                        rs.getString("addressDetail"),
                        rs.getString("addressBase")),
                getAddressParams);

    }

    /**
     * 주소 수정
     * @param addressId
     * @param patchAddressReq
     * @return
     */
    public int modifyAddress(int addressId, PatchAddressReq patchAddressReq) {
        String modifyAddressQuery = "update Address set name = ?, phoneNum = ?, addressName = ?, addressDetail = ?, addressBase = ? where addressId = ?";
        Object[] modifyAddressParams = new Object[] {patchAddressReq.getName(), patchAddressReq.getPhoneNum(), patchAddressReq.getAddressName(), patchAddressReq.getAddressDetail(), patchAddressReq.getAddressBase(), addressId};
        return this.jdbcTemplate.update(modifyAddressQuery, modifyAddressParams);
    }

    /**
     * 주소 삭제
     * @param addressId
     * @return
     */
    public int deleteAddress(int addressId) {
        String deleteAddressQuery = "UPDATE Address SET status = 'DELETED' WHERE addressId = ?";
        int deleteAddressParams = addressId;

        return this.jdbcTemplate.update(deleteAddressQuery, deleteAddressParams);

    }
}
