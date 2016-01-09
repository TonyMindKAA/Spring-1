package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.UserAccountDAO;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class UserAccountServiceMySql implements UserAccountService {

    @Autowired
    @Qualifier("userAccountDAOMySql")
    UserAccountDAO userAccountDAO;

    @Override
    public List<UserAccount> selectAll() {
        return userAccountDAO.selectAll();
    }

    @Override
    public UserAccount selectById(long id) {
        return userAccountDAO.selectById(id);
    }

    @Override
    public UserAccount insert(UserAccount userAccount) {
        return userAccountDAO.insert(userAccount);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        return userAccountDAO.update(userAccount);
    }

    @Override
    public boolean delete(UserAccount userAccount) {
        return userAccountDAO.delete(userAccount);
    }

    @Override
    public boolean deleteById(long id) {
        return userAccountDAO.deleteById(id);
    }

    @Override
    public boolean putMoneyIntoAccount(long userAccountId, long amountOfMoney) {
        if (amountOfMoney > 0) {
            UserAccount userAccount = selectById(userAccountId);
            userAccount.addMoneyToAccount(amountOfMoney);
            update(userAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdrawMoneyFromAccount(long userAccountId, long amountOfMoney) {
        if (amountOfMoney > 0) {
            UserAccount userAccount = selectById(userAccountId);
            if (userAccount.getPrepaidMoney() - amountOfMoney > 0) {
                userAccount.writeOffMoneyToAccount(amountOfMoney);
                update(userAccount);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
