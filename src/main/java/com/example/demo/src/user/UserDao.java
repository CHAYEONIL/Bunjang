package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from UserInfo where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUserParams);
    }
    

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User(name, userNickName, profileImageUrl, email, password, phoneNum, content) VALUES (?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getUserNickName(), postUserReq.getProfileImageUrl(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getPhoneNum(), postUserReq.getContent()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserInfo(PatchUserReq patchUserReq){
        String modifyUserContentQuery = "update User set userNickName =?, profileImageUrl =?, content = ? where userId = ? ";
        Object[] modifyUserContentParams = new Object[]{patchUserReq.getUserNickName(), patchUserReq.getProfileImageUrl(), patchUserReq.getContent(), patchUserReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserContentQuery,modifyUserContentParams);
    }

    public int modifyStatus(PatchUserStatusReq patchUserStatusReq){
        String modifyUserContentQuery = "update User set status =?  where userId = ? ";
        Object[] modifyUserContentParams = new Object[]{patchUserStatusReq.getStatus(), patchUserStatusReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserContentQuery,modifyUserContentParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userId, name, userNickName, profileImageUrl, email, password, phoneNum, content, status from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userId"),
                        rs.getString("name"),
                        rs.getString("userNickName"),
                        rs.getString("profileImageUrl"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phoneNum"),
                        rs.getString("content"),
                        rs.getString("status")
                ),
                getPwdParams
                );

    }


}
