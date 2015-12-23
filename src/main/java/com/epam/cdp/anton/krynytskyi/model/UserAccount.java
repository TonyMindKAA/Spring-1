package com.epam.cdp.anton.krynytskyi.model;


public class UserAccount {
    private long id;
    private long prepaidMoney;

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
}
