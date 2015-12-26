package com.epam.cdp.anton.krynytskyi.services;

import com.epam.cdp.anton.krynytskyi.model.UserAccount;

import java.util.List;

public interface UserAccountService {

    List<UserAccount> selectAll();

    UserAccount selectById(long id);

    UserAccount insert(UserAccount userAccount);

    UserAccount update(UserAccount userAccount);

    boolean delete(UserAccount userAccount);

    boolean deleteById(long id);

}
