package com.epam.cdp.anton.krynytskyi.model.impl;

import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.AbstractBean;

/**
 * @author Anton_Krynytskyi
 */
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
}
