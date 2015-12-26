package com.epam.cdp.anton.krynytskyi.model.impl;


import com.epam.cdp.anton.krynytskyi.model.UserAccount;

public class UserAccountBean implements UserAccount {
    private long id;
    private long prepaidMoney;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrepaidMoney() {
        return prepaidMoney;
    }

    public void setPrepaidMoney(long prepaidMoney) {
        this.prepaidMoney = prepaidMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountBean that = (UserAccountBean) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
