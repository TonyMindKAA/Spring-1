package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class UserServiceMySql implements UserService {

    @Autowired
    @Qualifier("userDAOMySql")
    private UserDAO userDAO;

    @Override
    public List<User> selectAll() {
        return userDAO.selectAll();
    }

    @Override
    public User selectById(long id) {
        return userDAO.selectById(id);
    }

    @Override
    public User insert(User user) {
        return userDAO.insert(user);
    }

    @Override
    public User update(User user) {
        return userDAO.update(user);
    }

    @Override
    public boolean delete(User user) {
        return userDAO.delete(user);
    }

    @Override
    public boolean deleteById(long id) {
        return userDAO.deleteById(id);
    }

    @Override
    public User selectByEmail(String email) {
        return userDAO.selectByEmail(email);
    }

    @Override
    public List<User> selectByName(String name, int pageSize, int pageNum) {
        return userDAO.selectByName(name, pageSize, pageNum);
    }
}
