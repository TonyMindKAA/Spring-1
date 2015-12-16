package com.epam.cdp.anton.krynytskyi.model;

import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;

import java.util.List;

public class BookingJSonObj {

    private List<EventBean> events;
    private List<UserBean> user;
    private List<TicketBean> tickets;

    public List<EventBean> getEvents() {
        return events;
    }

    public void setEvents(List<EventBean> events) {
        this.events = events;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<TicketBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketBean> tickets) {
        this.tickets = tickets;
    }
}
