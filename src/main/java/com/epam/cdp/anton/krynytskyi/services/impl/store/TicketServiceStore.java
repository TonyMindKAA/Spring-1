package com.epam.cdp.anton.krynytskyi.services.impl.store;

import com.epam.cdp.anton.krynytskyi.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class TicketServiceStore implements TicketService {

    @Autowired
    @Qualifier("ticketDAOStore")
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
