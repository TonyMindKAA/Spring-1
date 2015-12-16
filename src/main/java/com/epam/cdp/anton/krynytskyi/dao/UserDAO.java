package com.epam.cdp.anton.krynytskyi.dao;

import com.epam.cdp.anton.krynytskyi.model.User;

import java.util.List;

public interface UserDAO {

    List<User> selectAll();

    User selectById(long id);

    User insert(User user);

    User update(User user);

    boolean delete(User user);

    boolean deleteById(long id);

    User selectByEmail(String email);

    List<User> selectByName(String name, int pageSize, int pageNum);
}
