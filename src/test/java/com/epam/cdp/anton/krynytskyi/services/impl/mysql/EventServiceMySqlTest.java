package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.impl.mysql.EventDAOMySql;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
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
public class EventServiceMySqlTest {


    @InjectMocks
    private EventServiceMySql eventServiceMySql;

    @Mock
    private EventDAOMySql eventDAOMySql;

    @Test
    public void shouldReturnEmptyListOfEvents_whenInvokeSelectAll() {
        List<Event> events = new ArrayList<>();
        when(eventDAOMySql.selectAll()).thenReturn(events);

        assertThat(eventServiceMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertEvent_whenInvokeInsert() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
        }};

        when(eventDAOMySql.insert(event)).thenReturn(event);

        assertTrue(eventServiceMySql.insert(event).equals(event));
    }

    @Test
    public void shouldUpdateEvent_whenInvokeUpdate() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
            setId(135245l);
        }};

        when(eventDAOMySql.update(event)).thenReturn(event);

        assertTrue(eventServiceMySql.update(event).equals(event));
    }


    @Test
    public void shouldReturnEventById_whenInvokeSelectById() {
        Event event = new EventBean() {{
            setTitle("title");
            setTicketPrice(400);
            setDate(new Date(235453453453434334l));
            setId(135245l);
        }};

        when(eventDAOMySql.selectById(event.getId())).thenReturn(event);

        assertEquals(eventServiceMySql.selectById(event.getId()).getId(), event.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(eventDAOMySql.deleteById(id)).thenReturn(true);

        assertThat(eventServiceMySql.deleteById(id)).isTrue();
    }

    @Test
    public void shoutDeleteElementByUserObj_whenElementExist() {
        Event event = new EventBean() {{
            setId(135245l);
        }};

        when(eventDAOMySql.delete(event)).thenReturn(true);

        assertThat(eventServiceMySql.delete(event)).isTrue();
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
        int pageSize = 3;
        int pageNum = 1;

        when(eventDAOMySql.selectForDay(event.getDate(),pageSize,pageNum)).thenReturn(events);

        assertEquals(eventServiceMySql.selectForDay(event.getDate(), pageSize, pageNum).size(), events.size());
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
        int pageSize = 3;
        int pageNum = 1;

        when(eventDAOMySql.selectByTitle(event.getTitle(),pageSize,pageNum)).thenReturn(events);

        assertEquals(eventServiceMySql.selectByTitle(event.getTitle(), pageSize, pageNum).size(), events.size());
    }
}
