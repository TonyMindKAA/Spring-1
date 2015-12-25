package com.epam.cdp.anton.krynytskyi.dao.mysql;

import com.epam.cdp.anton.krynytskyi.dao.UserAccountDAO;
import com.epam.cdp.anton.krynytskyi.mapers.UserAccountRowMapper;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UserAccountDAOMySql implements UserAccountDAO {


    public static final String SELECT_ALL_USER_ACCOUNT = "select * from user_account";
    public static final String SELECT_USER_ACCOUNT_BY_ID = "select * from user_account where id = ?";
    public static final String INSERT_USER_ACCOUNT = "insert into user_account (userId, prepaid_money) values (?, ?)";
    public static final String UPDATE_USER_ACCOUNT = "update user_account set userId = ?, prepaid_money = ? where id = ?";
    public static final String DELETE_USER_ACCOUNT = "delete from user_account where id = ?";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public UserAccountDAOMySql() {
    }

    @Autowired
    public UserAccountDAOMySql(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<UserAccount> selectAll() {
        return jdbcTemplateObject.query(SELECT_ALL_USER_ACCOUNT, new UserAccountRowMapper());
    }

    @Override
    public UserAccount selectById(long id) {
        return jdbcTemplateObject.queryForObject(SELECT_USER_ACCOUNT_BY_ID,
                new Object[]{id},
                new UserAccountRowMapper());
    }

    @Override
    public UserAccount insert(UserAccount userAccount) {
        jdbcTemplateObject.update(INSERT_USER_ACCOUNT,
                userAccount.getUserId(),
                userAccount.getPrepaidMoney());
        return userAccount;
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        jdbcTemplateObject.update(UPDATE_USER_ACCOUNT,
                userAccount.getUserId(),
                userAccount.getPrepaidMoney(),
                userAccount.getId());
        return userAccount;
    }

    @Override
    public boolean delete(UserAccount userAccount) {
        return deleteById(userAccount.getId());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplateObject.update(DELETE_USER_ACCOUNT, id) > 0 ? true : false;
    }
}
