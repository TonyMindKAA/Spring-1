package com.epam.cdp.anton.krynytskyi.model.impl;

import com.epam.cdp.anton.krynytskyi.model.AbstractBean;
import com.epam.cdp.anton.krynytskyi.model.Event;

import java.util.Date;

public class EventBean extends AbstractBean implements Event {

    private long id;
    private String title;
    private Date date;
    private long ticketPrice;

    public long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventBean eventBean = (EventBean) o;

        return id == eventBean.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
