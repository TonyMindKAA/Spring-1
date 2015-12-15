package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import static com.epam.cdp.anton.krynytskyi.domain.model.Const.TICKET_BEAN;

import com.epam.cdp.anton.krynytskyi.api.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.model.Ticket;
import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.model.TicketBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDAOStore implements TicketDAO {

    public static final String PART_OF_ID = "ticket:";
    private BookingStore bookingStore;

    public void setBookingStore(BookingStore bookingStore) {
        this.bookingStore = bookingStore;
    }

    public List<Ticket> selectAll() {
        List<Object> objectList = bookingStore.readAll(TICKET_BEAN);
        List<Ticket> ticketList = new ArrayList<>();

        objectList.stream()
                .filter(elt -> elt != null)
                .forEach(elt -> ticketList.add(castToTicket(elt)));

        return ticketList;
    }

    private Ticket castToTicket(Object elt) {
        return ((Ticket) elt);
    }

    public Ticket selectById(long id) {
        Object readTicket = bookingStore.read(TICKET_BEAN + ":" + id);
        return readTicket != null ? (Ticket) readTicket : null;
    }

    public Ticket insert(Ticket ticket) {
        Object insertedTicket = bookingStore.create(TICKET_BEAN, ticket);
        return ticket != null ? (Ticket) insertedTicket : null;
    }

    public Ticket update(Ticket ticket) {
        Object updatedTicket = bookingStore.update(TICKET_BEAN + ":" + ticket.getId(), ticket);
        return ticket != null ? (Ticket) updatedTicket : null;
    }

    public boolean delete(Ticket ticket) {
        return ticket != null ? deleteById(ticket.getId()) : false;
    }

    public boolean deleteById(long id) {
        return bookingStore.delete(TICKET_BEAN + ":" + id);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket newTicket = new TicketBean();

        newTicket.setUserId(userId);
        newTicket.setEventId(eventId);
        newTicket.setPlace(place);
        newTicket.setCategory(category);

        return insert(newTicket);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> tickets = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return tickets.stream()
                .filter(e -> e.getUserId() == user.getId())
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> ticket = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return ticket.stream()
                .filter(e -> e.getEventId() == event.getId())
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}
