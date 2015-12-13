package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import com.epam.cdp.anton.krynytskyi.api.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.api.model.Ticket;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;

import java.util.List;

public class TicketDAOStore implements TicketDAO {

    public static final String PART_OF_ID = "ticket:";
    private BookingStore bookingStore;

    // TODO: think about it: return bookingStore.getAll();
    public List<Ticket> selectAll() {
        return null;
    }

    public Ticket selectById(long id) {
        Object readTicket = bookingStore.read(PART_OF_ID + id);
        return readTicket != null ? (Ticket) readTicket : null;
    }

    public Ticket insert(Ticket ticket) {
        Object insertedTicket = bookingStore.create(PART_OF_ID + ticket.getId(), ticket);
        return ticket != null ? (Ticket) insertedTicket : null;
    }

    public Ticket update(Ticket ticket) {
        Object updatedTicket = bookingStore.update(PART_OF_ID + ticket.getId(), ticket);
        return ticket != null ? (Ticket) updatedTicket : null;
    }

    public void delete(Ticket ticket) {
        if (ticket != null) {
            deleteById(ticket.getId());
        }
    }

    public void deleteById(long id) {
        bookingStore.read(PART_OF_ID + id);
    }

}
