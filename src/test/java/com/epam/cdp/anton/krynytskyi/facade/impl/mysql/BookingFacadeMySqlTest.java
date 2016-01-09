package com.epam.cdp.anton.krynytskyi.facade.impl.mysql;

import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserAccountBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import com.epam.cdp.anton.krynytskyi.services.EventService;
import com.epam.cdp.anton.krynytskyi.services.TicketService;
import com.epam.cdp.anton.krynytskyi.services.UserAccountService;
import com.epam.cdp.anton.krynytskyi.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeMySqlTest {

    @InjectMocks
    private BookingFacadeMySql bookingFacadeMySql;

    @Mock
    private EventService eventService;

    @Mock
    private TicketService ticketService;

    @Mock
    private UserService userService;

    @Mock
    private UserAccountService userAccountService;

    @Test
    public void shouldInsertEvent_whenInvokeInsert() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
        }};

        when(eventService.insert(event)).thenReturn(event);

        assertTrue(bookingFacadeMySql.createEvent(event).equals(event));
    }

    @Test
    public void shouldUpdateEvent_whenInvokeUpdate() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
            setId(135245l);
        }};

        when(eventService.update(event)).thenReturn(event);

        assertTrue(bookingFacadeMySql.updateEvent(event).equals(event));
    }


    @Test
    public void shouldReturnEventById_whenInvokeSelectById() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334L));
            setId(135245L);
        }};

        when(eventService.selectById(event.getId())).thenReturn(event);

        assertEquals(bookingFacadeMySql.getEventById(event.getId()).getId(), event.getId());
    }

    @Test
    public void shoutDeleteEventById_whenElementExist() {
        long id = 222;

        when(eventService.deleteById(id)).thenReturn(true);

        assertThat(bookingFacadeMySql.deleteEvent(id)).isTrue();
    }

    @Test
    public void shouldReturnListWithOneEvent_whenEventWithCurrentDayExist() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334L));
        }};
        List<Event> events = new ArrayList<>();
        events.add(event);
        int pageSize = 3;
        int pageNum = 1;

        when(eventService.selectForDay(event.getDate(), pageSize, pageNum)).thenReturn(events);

        assertEquals(bookingFacadeMySql.getEventsForDay(event.getDate(), pageSize, pageNum).size(), events.size());
    }

    @Test
    public void shouldReturnListWithOneEvent_whenEventWithCurrentTittleExist() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334L));
        }};
        List<Event> events = new ArrayList<>();
        events.add(event);
        int pageSize = 3;
        int pageNum = 1;

        when(eventService.selectByTitle(event.getTitle(), pageSize, pageNum)).thenReturn(events);

        assertEquals(bookingFacadeMySql.getEventsByTitle(event.getTitle(), pageSize, pageNum).size(), events.size());
    }

    @Test
    public void shoutCancelTicketById_whenElementExist() {
        long id = 222;

        when(ticketService.deleteById(id)).thenReturn(true);

        assertThat(bookingFacadeMySql.cancelTicket(id)).isTrue();
    }

    @Test
    public void shouldReturnBookedTiket_whenInUserAccountEnoughMoney() {
        Ticket ticket = new TicketBean();
        ticket.setCategory(Ticket.Category.BAR);
        ticket.setEventId(23423L);
        ticket.setPlace(23);
        ticket.setUserId(423434L);

        UserAccount userAccount = new UserAccountBean();
        userAccount.setUserId(222222L);
        userAccount.setId(333333L);
        userAccount.setPrepaidMoney(7000L);

        UserAccount userAccountAfterBooking = new UserAccountBean();
        userAccountAfterBooking.setUserId(222222L);
        userAccountAfterBooking.setId(333333L);
        userAccountAfterBooking.setPrepaidMoney(300L);


        Event event = new EventBean();
        event.setTicketPrice(400L);
        event.setId(111111L);

        when(ticketService.bookTicket(222222L, 111111L, ticket.getPlace(), ticket.getCategory())).thenReturn(ticket);

        Ticket actual = bookingFacadeMySql.bookTicket(222222L, 111111L, ticket.getPlace(), ticket.getCategory());
        assertTrue(actual.equals(ticket));
    }

    @Test
    public void shouldReturnListWithOneTicket_whenTicketWithCurrentUserExist() {
        Ticket ticket = new TicketBean() {{
            setCategory(Category.BAR);
            setEventId(23423L);
            setPlace(23);
            setUserId(423434L);
        }};

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        User user = new UserBean() {{
            setId(24235245L);
        }};
        int pageSize = 3;
        int pageNum = 1;

        when(ticketService.getBookedTickets(user, pageSize, pageNum)).thenReturn(tickets);

        assertEquals(bookingFacadeMySql.getBookedTickets(user, pageSize, pageNum).size(), tickets.size());
    }

    @Test
    public void shouldReturnListWithOneTicket_whenTicketWithCurrentTicketExist() {
        Ticket ticket = new TicketBean() {{
            setCategory(Category.BAR);
            setEventId(23423L);
            setPlace(23);
            setUserId(423434L);
        }};

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        Event event = new EventBean() {{
            setId(24235245L);
        }};
        int pageSize = 3;
        int pageNum = 1;

        when(ticketService.getBookedTickets(event, pageSize, pageNum)).thenReturn(tickets);

        assertEquals(bookingFacadeMySql.getBookedTickets(event, pageSize, pageNum).size(), tickets.size());
    }

    @Test
    public void shouldInsertUser_whenInvokeInsert() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
        }};

        when(userService.insert(user)).thenReturn(user);

        assertTrue(bookingFacadeMySql.createUser(user).equals(user));
    }

    @Test
    public void shouldUpdateUser_whenInvokeUpdate() {
        UserBean user = new UserBean();
        user.setName("bob");
        user.setEmail("bob@email.ru");
        user.setId(2452435);

        when(userService.update(user)).thenReturn(user);

        assertTrue(bookingFacadeMySql.updateUser(user).equals(user));
    }

    @Test
    public void shouldReturnUserById_whenInvokeSelectById() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
            setId(2452435);
        }};

        when(userService.selectById(user.getId())).thenReturn(user);

        assertEquals(bookingFacadeMySql.getUserById(user.getId()).getId(), user.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(userService.deleteById(id)).thenReturn(true);

        assertThat(bookingFacadeMySql.deleteUser(id)).isTrue();
    }

    @Test
    public void shouldSelectUserByEmail_whenUserWithCurrentEmailExist() {
        UserBean userBean = new UserBean() {{
            setEmail("sample@email.com");
        }};

        when(userService.selectByEmail(userBean.getEmail())).thenReturn(userBean);

        assertThat(bookingFacadeMySql.getUserByEmail(userBean.getEmail()).equals(userBean)).isTrue();
    }

    @Test
    public void shouldReturnListWithOneUser_when() {
        UserBean userBean = new UserBean() {{
            setName("sampleName");
        }};
        List<User> users = new ArrayList<>();
        users.add(userBean);
        int pageSize = 3;
        int pageNum = 1;

        when(userService.selectByName(userBean.getName(), pageSize, pageNum)).thenReturn(users);

        assertEquals(bookingFacadeMySql.getUsersByName(userBean.getName(), pageSize, pageNum).size(), users.size());
    }

    @Test
    public  void shouldPutMoneyIntoAccount_whenMoneyNotNegativeNumber () {
        long userAccountId = 11234123L;
        long amountOfMoney = 7000L;

        when(userAccountService.putMoneyIntoAccount(userAccountId,amountOfMoney)).thenReturn(true);

        assertThat(bookingFacadeMySql.putMoneyIntoAccount(userAccountId,amountOfMoney)).isTrue();
    }

    @Test
    public  void shouldNotPutMoneyIntoAccount_whenMoneyNegativeNumber () {
        long userAccountId = 11234123L;
        long amountOfMoney = - 7000L;

        assertThat(bookingFacadeMySql.putMoneyIntoAccount(userAccountId,amountOfMoney)).isFalse();
    }

    @Test
    public  void shouldWithdrawMoneyFromAccount_whenAmountOfMoneyNotNegativeNumberAndEnoughMoney () {
        long userAccountId = 11234123L;
        long amountOfMoney = 7000L;

        when(userAccountService.withdrawMoneyFromAccount(userAccountId,amountOfMoney)).thenReturn(true);

        assertThat(bookingFacadeMySql.withdrawMoneyFromAccount(userAccountId,amountOfMoney)).isTrue();
    }

    @Test
    public  void shouldNotWithdrawMoneyFromAccount_whenAmountOfMoneyNotNegativeNumberAndNotEnoughMoney () {
        long userAccountId = 11234123L;
        long amountOfMoney = 7000L;

        when(userAccountService.withdrawMoneyFromAccount(userAccountId,amountOfMoney)).thenReturn(false);

        assertThat(bookingFacadeMySql.withdrawMoneyFromAccount(userAccountId,amountOfMoney)).isFalse();
    }

    @Test
    public  void shouldNotWithdrawMoneyFromAccount_whenAmountOfMoneyNegativeNumber () {
        long userAccountId = 11234123L;
        long amountOfMoney = -7000L;

        assertThat(bookingFacadeMySql.withdrawMoneyFromAccount(userAccountId,amountOfMoney)).isFalse();
    }

}
