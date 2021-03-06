package com.epam.cdp.anton.krynytskyi.dao.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.mapers.UserRowMapper;
import com.epam.cdp.anton.krynytskyi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDAOMySql implements UserDAO {

    public static final String SELECT_ALL_USER = "select * from user";
    public static final String SELECT_USER_BY_ID = "select * from user where id = ?";
    public static final String INSERT_USER = "insert into user (name, email) values (?, ?)";
    public static final String UPDATE_USER = "update user set name = ?, email = ? where id = ?";
    public static final String DELETE_USER = "delete from user where id = ?";
    public static final String SELECT_USER_BY_EMAIL = "select * from user where email = ?";
    public static final String SELECT_USER_BY_NAME = "select * from user where name = ? limit ?, ?";

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Autowired
    private UserRowMapper userRowMapper;

    public UserDAOMySql() {
    }

    @Override
    public List<User> selectAll() {
        return jdbcTemplateObject.query(SELECT_ALL_USER,
                userRowMapper);
    }

    @Override
    public User selectById(long id) {
        return jdbcTemplateObject.queryForObject(SELECT_USER_BY_ID, new Object[]{id}, userRowMapper);
    }

    @Override
    public User insert(User user) {
        jdbcTemplateObject.update(INSERT_USER,
                user.getName(),
                user.getEmail());
        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplateObject.update(UPDATE_USER,
                user.getName(),
                user.getEmail(),
                user.getId());
        return user;
    }

    @Override
    public boolean delete(User user) {
        return deleteById(user.getId());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplateObject.update(DELETE_USER, id) > 0 ? true : false;
    }

    @Override
    public User selectByEmail(String email) {
        return jdbcTemplateObject.queryForObject(SELECT_USER_BY_EMAIL,
                new Object[]{email},
                userRowMapper);
    }

    @Override
    public List<User> selectByName(String name, int pageSize, int pageNum) {
        int paginationSize = pageSize * (pageNum - 1);
        return jdbcTemplateObject.query(SELECT_USER_BY_NAME,
                new Object[]{name,
                        paginationSize,
                        pageSize},
                userRowMapper);
    }

}
