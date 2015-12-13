package com.epam.cdp.anton.krynytskyi.domain.services;

import com.epam.cdp.anton.krynytskyi.api.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.model.Ticket;
import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.api.services.TicketService;

import java.util.List;

public class TicketServiceStore implements TicketService {

    private TicketDAO ticketDAO;

    public List<Ticket> selectAll() {
        return ticketDAO.selectAll();
    }

    public Ticket selectById(long id) {
        return ticketDAO.selectById(id);
    }

    public Ticket insert(Ticket ticket) {
        return ticketDAO.insert(ticket);
    }

    public Ticket update(Ticket ticket) {
        return ticketDAO.update(ticket);
    }

    public boolean delete(Ticket ticket) {
        return ticketDAO.delete(ticket);
    }

    public boolean deleteById(long id) {
        return ticketDAO.deleteById(id);
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketDAO.bookTicket(userId, eventId, place, category);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(event, pageSize, pageNum);
    }
}
