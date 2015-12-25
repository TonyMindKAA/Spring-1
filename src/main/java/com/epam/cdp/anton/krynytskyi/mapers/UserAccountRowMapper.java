package com.epam.cdp.anton.krynytskyi.mapers;

import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.model.impl.UserAccountBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountRowMapper implements RowMapper<UserAccount> {
    @Override
    public UserAccount mapRow(ResultSet rs, int i) throws SQLException {
        UserAccount userAccount = new UserAccountBean();
        userAccount.setId(rs.getInt("id"));
        userAccount.setPrepaidMoney(rs.getInt("prepaid_money"));
        userAccount.setUserId(rs.getInt("userId"));
        return userAccount;
    }
}
