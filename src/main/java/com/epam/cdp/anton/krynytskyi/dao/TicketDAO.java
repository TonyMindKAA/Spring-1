package com.epam.cdp.anton.krynytskyi.dao;

import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;

import java.util.List;

public interface TicketDAO {

    List<Ticket> selectAll();

    Ticket selectById(long id);

    Ticket insert(Ticket ticket);

    Ticket update(Ticket ticket);

    boolean delete(Ticket ticket);

    boolean deleteById(long id);

    Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);
}
