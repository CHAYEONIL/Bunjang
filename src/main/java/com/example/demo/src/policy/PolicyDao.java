package com.example.demo.src.policy;

import com.example.demo.src.policy.model.GetPolicyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class PolicyDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetPolicyRes> getPolicy() {
        String getPolicyQuery = "select * from Policy";
        return this.jdbcTemplate.query(getPolicyQuery,
                (rs, rowNum) -> new GetPolicyRes(
                        rs.getInt("policyId"),
                        rs.getString("content"))
        );
    }
}
