package com.epam.cdp.anton.krynytskyi.domain.facade;

import com.epam.cdp.anton.krynytskyi.api.facade.BookingFacade;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.model.Ticket;
import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.api.services.EventService;
import com.epam.cdp.anton.krynytskyi.api.services.TicketService;
import com.epam.cdp.anton.krynytskyi.api.services.UserService;

import java.util.Date;
import java.util.List;

public class BookingFacadeImpl implements BookingFacade {

    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;

    public BookingFacadeImpl(EventService eventService,
                             TicketService ticketService,
                             UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public BookingFacadeImpl() {}

    public Event getEventById(long id) {
        return eventService.selectById(id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.selectByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.selectForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventService.insert(event);
    }

    public Event updateEvent(Event event) {
        return eventService.update(event);
    }

    public boolean deleteEvent(long eventId) {
        return eventService.deleteById(eventId);
    }

    public User getUserById(long id) {
        return userService.selectById(id);
    }

    public User getUserByEmail(String email) {
        return userService.selectByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.selectByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        return userService.insert(user);
    }

    public User updateUser(User user) {
        return userService.update(user);
    }

    public boolean deleteUser(long userId) {
        return userService.deleteById(userId);
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        return ticketService.deleteById(ticketId);
    }
}
