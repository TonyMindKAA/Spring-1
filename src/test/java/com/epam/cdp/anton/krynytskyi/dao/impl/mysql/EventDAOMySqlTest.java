package com.epam.cdp.anton.krynytskyi.dao.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.impl.mysql.EventDAOMySql;
import com.epam.cdp.anton.krynytskyi.mapers.EventRowMapper;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EventDAOMySqlTest {

    public static final String SELECT_ALL_EVENTS = "select * from event";
    public static final String SELECT_EVENT_BY_ID = "select * from event where id = ?";
    public static final String INSERT_EVENT = "insert into event (title, ticket_price, date) values (?, ?)";
    public static final String UPDATE_EVENT = "update event set title = ?, ticket_price = ? date = ? where id = ?";
    public static final String DELETE_EVENT = "delete from event where id = ?";
    public static final String SELECT_EVENT_BY_TITLE = "select * from event where title = ? limit ?, ?";
    public static final String SELECT_EVENT_BY_DATE = "select * from event where name = ? limit ?, ?";

    @InjectMocks
    private EventDAOMySql eventDAOMySql;

    @Mock
    private JdbcTemplate jdbcTemplateObject;

    @Mock
    private EventRowMapper eventRowMapper;

    @Test
    public void shouldReturnEmptyListOfEvents_whenInvokeSelectAll() {
        List<Event> events = new ArrayList<>();
        when(jdbcTemplateObject.query(SELECT_ALL_EVENTS, eventRowMapper)).thenReturn(events);

        assertThat(eventDAOMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertEvent_whenInvokeInsert() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
        }};

        when(jdbcTemplateObject.update(INSERT_EVENT,
                event.getTitle(),
                event.getTicketPrice(),
                event.getDate())).thenReturn(1);

        assertTrue(eventDAOMySql.insert(event).equals(event));
    }

    @Test
    public void shouldUpdateEvent_whenInvokeUpdate() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
            setId(135245l);
        }};

        when(jdbcTemplateObject.update(UPDATE_EVENT,
                event.getTitle(),
                event.getTicketPrice(),
                event.getDate(),
                event.getId())).thenReturn(1);

        assertTrue(eventDAOMySql.insert(event).equals(event));
    }


    @Test
    public void shouldReturnEventById_whenInvokeSelectById() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
            setId(135245l);
        }};

        when(jdbcTemplateObject.queryForObject(SELECT_EVENT_BY_ID,
                new Object[]{event.getId()},
                eventRowMapper)).thenReturn(event);

        assertEquals(eventDAOMySql.selectById(event.getId()).getId(), event.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(jdbcTemplateObject.update(DELETE_EVENT, id)).thenReturn(1);

        assertThat(eventDAOMySql.deleteById(id)).isTrue();
    }

    @Test
    public void shoutDeleteElementByUserObj_whenElementExist() {
        Event event = new EventBean() {{
            setId(135245l);
        }};

        when(jdbcTemplateObject.update(DELETE_EVENT, event.getId())).thenReturn(1);

        assertThat(eventDAOMySql.delete(event)).isTrue();
    }

    @Test
    public void shouldReturnListWithOneEvent_whenEventWithCurrentDayExist() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
        }};
        List<Event> events = new ArrayList<>();
        events.add(event);
        int paginationSize = 0;
        int pageSize = 3;
        int pageNum = 1;

        when(jdbcTemplateObject.query(SELECT_EVENT_BY_DATE,
                new Object[]{"7463192-12-01",
                        paginationSize,
                        pageSize},
                eventRowMapper)).thenReturn(events);

        assertEquals(eventDAOMySql.selectForDay(event.getDate(), pageSize, pageNum).size(), events.size());
    }

    @Test
    public void shouldReturnListWithOneEvent_whenEventWithCurrentTittleExist() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
        }};
        List<Event> events = new ArrayList<>();
        events.add(event);
        int paginationSize = 0;
        int pageSize = 3;
        int pageNum = 1;

        when(jdbcTemplateObject.query(SELECT_EVENT_BY_TITLE,
                new Object[]{event.getTitle(),
                        paginationSize,
                        pageSize},
                eventRowMapper)).thenReturn(events);

        assertEquals(eventDAOMySql.selectByTitle(event.getTitle(), pageSize, pageNum).size(), events.size());
    }
}
