package com.epam.cdp.anton.krynytskyi.domain.setter.id;

import com.epam.cdp.anton.krynytskyi.api.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.domain.model.UserBean;

public class SetterIdUserBean implements SetterId<UserBean> {

    public UserBean setId(Object obj, long id) {
        UserBean user = (UserBean) obj;
        user.setId(id);
        return user;
    }
}
