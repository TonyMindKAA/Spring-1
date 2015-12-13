package com.epam.cdp.anton.krynytskyi.domain.model;

import com.epam.cdp.anton.krynytskyi.api.model.Ticket;

/**
 * @author Anton_Krynytskyi
 */
public class TicketBean implements Ticket {

    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return this.eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return this.place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
