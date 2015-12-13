package com.epam.cdp.anton.krynytskyi.domain.model;
import com.epam.cdp.anton.krynytskyi.api.model.Event;

import java.util.Date;

public class EventBean implements Event{

    private long id;
    private String title;
    private Date date;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
