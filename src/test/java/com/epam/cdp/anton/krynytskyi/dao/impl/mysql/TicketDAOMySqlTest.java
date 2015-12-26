package com.epam.cdp.anton.krynytskyi.dao.impl.mysql;

import com.epam.cdp.anton.krynytskyi.mapers.TicketRowMapper;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketDAOMySqlTest {

    public static final String SELECT_ALL_TICKETS = "select * from ticket";
    public static final String SELECT_TICKET_BY_ID = "select * from ticket where id = ?";
    public static final String INSERT_TICKET = "insert into ticket (category, eventId, place, userId) values (?, ?, ?, ?)";
    public static final String UPDATE_TICKET = "update ticket set category = ?, eventId = ?, place = ?, userId = ? where id = ?";
    public static final String DELETE_TICKET = "delete from ticket where id = ?";
    public static final String SELECT_TICKET_BY_USER = "select * from ticket where userId = ? limit ?, ?";
    public static final String SELECT_TICKET_BY_EVENT = "select * from ticket where eventId = ? limit ?, ?";

    @InjectMocks
    private TicketDAOMySql ticketDAOMySql;

    @Mock
    private JdbcTemplate jdbcTemplateObject;

    @Mock
    private TicketRowMapper ticketRowMapper;

    @Test
    public void shouldReturnEmptyListOfEvents_whenInvokeSelectAll() {
        List<Ticket> events = new ArrayList<>();
        when(jdbcTemplateObject.query(SELECT_ALL_TICKETS, ticketRowMapper)).thenReturn(events);

        assertThat(ticketDAOMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertTicket_whenInvokeInsert() {
        Ticket ticket = new TicketBean() {{
            setCategory(Category.BAR);
            setEventId(23423L);
            setPlace(23);
            setUserId(423434L);
        }};

        when(jdbcTemplateObject.update(INSERT_TICKET,
                "BAR",
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getUserId())).thenReturn(1);

        assertTrue(ticketDAOMySql.insert(ticket).equals(ticket));
    }

    @Test
    public void shouldUpdateTicket_whenInvokeUpdate() {
        Ticket ticket = new TicketBean() {{
            setCategory(Category.BAR);
            setEventId(23423L);
            setPlace(23);
            setUserId(423434L);
            setId(12343l);
        }};

        when(jdbcTemplateObject.update(UPDATE_TICKET,
                "BAR",
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getUserId(),
                ticket.getId())).thenReturn(1);

        assertTrue(ticketDAOMySql.update(ticket).equals(ticket));
    }


    @Test
    public void shouldReturnTicketById_whenInvokeSelectById() {
        Ticket ticket = new TicketBean() {{
            setId(12343l);
        }};

        when(jdbcTemplateObject.queryForObject(SELECT_TICKET_BY_ID,
                new Object[]{ticket.getId()},
                ticketRowMapper)).thenReturn(ticket);

        assertEquals(ticketDAOMySql.selectById(ticket.getId()).getId(), ticket.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(jdbcTemplateObject.update(DELETE_TICKET, id)).thenReturn(1);

        assertThat(ticketDAOMySql.deleteById(id)).isTrue();
    }

    @Test
    public void shoutDeleteElementByUserObj_whenElementExist() {
        Ticket ticket = new TicketBean() {{
            setId(12343l);
        }};

        when(jdbcTemplateObject.update(DELETE_TICKET, ticket.getId())).thenReturn(1);

        assertThat(ticketDAOMySql.delete(ticket)).isTrue();
    }

    @Test
    public void shouldReturnListWithOneTicket_whenTicketWithCurrentDayExist() {
        Ticket ticket = new TicketBean();
        ticket.setCategory(Ticket.Category.BAR);
        ticket.setEventId(23423L);
        ticket.setPlace(23);
        ticket.setUserId(423434L);

        when(jdbcTemplateObject.update(INSERT_TICKET,
                "BAR",
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getUserId())).thenReturn(1);


        Ticket actual = ticketDAOMySql.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        assertTrue(actual
                .equals(ticket));
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
            setId(24235245l);
        }};
        int paginationSize = 0;
        int pageSize = 3;
        int pageNum = 1;

        when(jdbcTemplateObject.query(SELECT_TICKET_BY_USER,
                new Object[]{user.getId(),
                        paginationSize,
                        pageSize},
                ticketRowMapper)).thenReturn(tickets);

        assertEquals(ticketDAOMySql.getBookedTickets(user, pageSize, pageNum).size(), tickets.size());
    }

    @Test
    public void shouldReturnListWithOneTicket_whenTicketWithCurrentEventExist() {
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
        int paginationSize = 0;
        int pageSize = 3;
        int pageNum = 1;

        when(jdbcTemplateObject.query(SELECT_TICKET_BY_EVENT,
                new Object[]{event.getId(),
                        paginationSize,
                        pageSize},
                ticketRowMapper)).thenReturn(tickets);

        assertEquals(ticketDAOMySql.getBookedTickets(event, pageSize, pageNum).size(), tickets.size());
    }

}
