package com.epam.cdp.anton.krynytskyi.facade.impl.store;

import com.epam.cdp.anton.krynytskyi.facade.BookingFacade;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.services.EventService;
import com.epam.cdp.anton.krynytskyi.services.TicketService;
import com.epam.cdp.anton.krynytskyi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

public class BookingFacadeStore implements BookingFacade {

    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;

    @Autowired
    public BookingFacadeStore(@Qualifier("eventServiceStore") EventService eventService,
                              @Qualifier("ticketServiceStore") TicketService ticketService,
                              @Qualifier("userServiceStore") UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public BookingFacadeStore() {
    }

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

    @Override
    public boolean putMoneyIntoAccount(long userAccountId, long amountOfMoney) {
        return false;
    }

    @Override
    public boolean withdrawMoneyFromAccount(long userAccountId, long amountOfMoney) {
        return false;
    }

    public int size() {
        int size = eventService.selectAll().size() +
                userService.selectAll().size() +
                ticketService.selectAll().size();
        return size;
    }
}
