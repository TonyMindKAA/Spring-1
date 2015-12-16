package com.epam.cdp.anton.krynytskyi.services.impl;

import com.epam.cdp.anton.krynytskyi.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.services.UserService;

import java.util.List;

public class UserServiceStore implements UserService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> selectAll() {
        return userDAO.selectAll();
    }

    public User selectById(long id) {
        return userDAO.selectById(id);
    }

    public User insert(User user) {
        return userDAO.insert(user);
    }

    public User update(User user) {
        return userDAO.update(user);
    }

    public boolean delete(User user) {
        return userDAO.delete(user);
    }

    public boolean deleteById(long id) {
        return userDAO.deleteById(id);
    }

    public User selectByEmail(String email) {
        return userDAO.selectByEmail(email);
    }

    public List<User> selectByName(String name, int pageSize, int pageNum) {
        return userDAO.selectByName(name, pageSize, pageNum);
    }
}
