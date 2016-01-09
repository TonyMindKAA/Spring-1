package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.dao.UserAccountDAO;
import com.epam.cdp.anton.krynytskyi.dao.impl.mysql.TicketDAOMySql;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserAccountBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceMySqlTest {


    @InjectMocks
    private TicketServiceMySql ticketServiceMySql;

    @Mock
    private TicketDAOMySql ticketDAOMySql;

    @Mock
    private UserAccountDAO userAccountDAO;

    @Mock
    private EventDAO eventDAO;

    @Mock
    private TransactionDefinition def;

    @Mock
    private TransactionStatus status;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Test
    public void shouldReturnEmptyListOfEvents_whenInvokeSelectAll() {
        List<Ticket> events = new ArrayList<>();
        when(ticketDAOMySql.selectAll()).thenReturn(events);

        assertThat(ticketServiceMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertTicket_whenInvokeInsert() {
        Ticket ticket = new TicketBean() {{
            setCategory(Category.BAR);
            setEventId(23423L);
            setPlace(23);
            setUserId(423434L);
        }};

        when(ticketDAOMySql.insert(ticket)).thenReturn(ticket);

        assertTrue(ticketServiceMySql.insert(ticket).equals(ticket));
    }

    @Test
    public void shouldUpdateTicket_whenInvokeUpdate() {
        Ticket ticket = new TicketBean() {{
            setCategory(Category.BAR);
            setEventId(23423L);
            setPlace(23);
            setUserId(423434L);
            setId(12343L);
        }};

        when(ticketDAOMySql.update(ticket)).thenReturn(ticket);

        assertTrue(ticketServiceMySql.update(ticket).equals(ticket));
    }


    @Test
    public void shouldReturnTicketById_whenInvokeSelectById() {
        Ticket ticket = new TicketBean() {{
            setId(12343L);
        }};

        when(ticketDAOMySql.selectById(ticket.getId())).thenReturn(ticket);

        assertEquals(ticketServiceMySql.selectById(ticket.getId()).getId(), ticket.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(ticketDAOMySql.deleteById(id)).thenReturn(true);

        assertThat(ticketServiceMySql.deleteById(id)).isTrue();
    }

    @Test
    public void shoutDeleteElementByUserObj_whenElementExist() {
        Ticket ticket = new TicketBean() {{
            setId(12343L);
        }};

        when(ticketDAOMySql.delete(ticket)).thenReturn(true);

        assertThat(ticketServiceMySql.delete(ticket)).isTrue();
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

        when(transactionManager.getTransaction(def)).thenReturn(anyObject());
        when(userAccountDAO.selectByUserId(222222L)).thenReturn(userAccount);
        when(eventDAO.selectById(111111L)).thenReturn(event);
        when(userAccountDAO.update(userAccountAfterBooking)).thenReturn(userAccountAfterBooking);
        when(ticketDAOMySql.bookTicket(222222L, 111111L, ticket.getPlace(), ticket.getCategory())).thenReturn(ticket);

        Ticket actual = ticketServiceMySql.bookTicket(222222L, 111111L, ticket.getPlace(), ticket.getCategory());
        assertTrue(actual.equals(ticket));
    }

    @Test
    public void shouldReturnBookedTiketAsNull_whenInUserAccountNotEnoughMoney() {
        Ticket ticket = new TicketBean();
        ticket.setCategory(Ticket.Category.BAR);
        ticket.setEventId(23423L);
        ticket.setPlace(23);
        ticket.setUserId(423434L);

        UserAccount userAccount = new UserAccountBean();
        userAccount.setUserId(222222L);
        userAccount.setId(333333L);
        userAccount.setPrepaidMoney(300L);

        UserAccount userAccountAfterBooking = new UserAccountBean();
        userAccountAfterBooking.setUserId(222222L);
        userAccountAfterBooking.setId(333333L);
        userAccountAfterBooking.setPrepaidMoney(-100L);


        Event event = new EventBean();
        event.setTicketPrice(400L);
        event.setId(111111L);

        when(transactionManager.getTransaction(def)).thenReturn(anyObject());
        when(userAccountDAO.selectByUserId(222222L)).thenReturn(userAccount);
        when(eventDAO.selectById(111111L)).thenReturn(event);
        when(userAccountDAO.update(userAccountAfterBooking)).thenReturn(userAccountAfterBooking);
        doNothing().when(transactionManager).rollback(null);

        Ticket actual = ticketServiceMySql.bookTicket(222222L, 111111L, ticket.getPlace(), ticket.getCategory());
        assertNull(actual);
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

        when(ticketDAOMySql.getBookedTickets(user, pageSize, pageNum)).thenReturn(tickets);

        assertEquals(ticketServiceMySql.getBookedTickets(user, pageSize, pageNum).size(), tickets.size());
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

        when(ticketDAOMySql.getBookedTickets(event, pageSize, pageNum)).thenReturn(tickets);

        assertEquals(ticketServiceMySql.getBookedTickets(event, pageSize, pageNum).size(), tickets.size());
    }



}
