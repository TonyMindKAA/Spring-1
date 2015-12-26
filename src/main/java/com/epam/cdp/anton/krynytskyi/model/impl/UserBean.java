package com.epam.cdp.anton.krynytskyi.model.impl;

import com.epam.cdp.anton.krynytskyi.model.AbstractBean;
import com.epam.cdp.anton.krynytskyi.model.User;

public class UserBean extends AbstractBean implements User {

    private long id;
    private String name;
    private String email;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        if (id != userBean.id) return false;
        return !(email != null ? !email.equals(userBean.email) : userBean.email != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
