package com.epam.cdp.anton.krynytskyi.facade.impl.mysql;

import com.epam.cdp.anton.krynytskyi.facade.BookingFacade;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.services.EventService;
import com.epam.cdp.anton.krynytskyi.services.TicketService;
import com.epam.cdp.anton.krynytskyi.services.UserAccountService;
import com.epam.cdp.anton.krynytskyi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

public class BookingFacadeMySql implements BookingFacade {

    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;
    private UserAccountService userAccountService;

    @Autowired
    public BookingFacadeMySql(@Qualifier("eventServiceMySql") EventService eventService,
                              @Qualifier("ticketServiceMySql") TicketService ticketService,
                              @Qualifier("userServiceMySql") UserService userService,
                              @Qualifier("userAccountServiceMySql") UserAccountService userAccountService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.userAccountService = userAccountService;
    }

    public BookingFacadeMySql() {
    }

    @Override
    public Event getEventById(long id) {
        return eventService.selectById(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.selectByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.selectForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.insert(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.update(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.deleteById(eventId);
    }

    @Override
    public User getUserById(long id) {
        return userService.selectById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.selectByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.selectByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        return userService.insert(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userService.deleteById(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.deleteById(ticketId);
    }



    @Override
    public boolean putMoneyIntoAccount(long userAccountId, long amountOfMoney) {
        return userAccountService.putMoneyIntoAccount(userAccountId, amountOfMoney);
    }

    @Override
    public boolean withdrawMoneyFromAccount(long userAccountId, long amountOfMoney) {
        return userAccountService.withdrawMoneyFromAccount(userAccountId, amountOfMoney);
    }

    public int size() {
        int size = eventService.selectAll().size() +
                userService.selectAll().size() +
                ticketService.selectAll().size();
        return size;
    }
}
