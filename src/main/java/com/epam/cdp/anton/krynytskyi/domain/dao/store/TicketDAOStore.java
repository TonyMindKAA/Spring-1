package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import com.epam.cdp.anton.krynytskyi.api.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.model.Ticket;
import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.model.TicketBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicketDAOStore implements TicketDAO {

    public static final String PART_OF_ID = "ticket:";
    private BookingStore bookingStore;

    public void setBookingStore(BookingStore bookingStore) {
        this.bookingStore = bookingStore;
    }

    public List<Ticket> selectAll() {
        List<Object> objectList = bookingStore.readAll(PART_OF_ID);
        List<Ticket> eventList = new ArrayList<>();

        objectList.stream()
                .filter(elt -> elt != null)
                .forEach(elt -> eventList.add(castToTicket(elt)));

        return eventList;
    }

    private Ticket castToTicket(Object elt) {
        return ((Ticket) elt);
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

    public boolean delete(Ticket ticket) {
        return ticket != null ? deleteById(ticket.getId()) : false;
    }

    public boolean deleteById(long id) {
        return bookingStore.delete(PART_OF_ID + id);
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
        List<Ticket> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return events.stream()
                .filter(e -> e.getUserId() == user.getId())
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return events.stream()
                .filter(e -> e.getEventId() == event.getId())
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}
