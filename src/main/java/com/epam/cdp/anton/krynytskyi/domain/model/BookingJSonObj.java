package com.epam.cdp.anton.krynytskyi.domain.model;

import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.model.Ticket;
import com.epam.cdp.anton.krynytskyi.api.model.User;

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
