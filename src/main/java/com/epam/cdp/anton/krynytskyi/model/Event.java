package com.epam.cdp.anton.krynytskyi.model;

import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
public interface Event {

    long getId();

    void setId(long id);

    String getTitle();

    void setTitle(String title);

    Date getDate();

    void setDate(Date date);

    long getTicketPrice();

    void setTicketPrice(long ticketPrice);
}
