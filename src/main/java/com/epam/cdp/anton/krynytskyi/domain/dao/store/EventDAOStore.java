package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import com.epam.cdp.anton.krynytskyi.api.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventDAOStore implements EventDAO {

    public static final String PART_OF_ID = "event:";
    private BookingStore bookingStore;

    public List<Event> selectAll() {
        return bookingStore.readAll(PART_OF_ID);
    }

    public Event selectById(long id) {
        Object read = bookingStore.read(PART_OF_ID + id);
        return read != null ? (Event) read : null;
    }

    public Event insert(Event event) {
        Object insertedEvent = bookingStore.create(PART_OF_ID + event.getId(), event);
        return event != null ? (Event) insertedEvent : null;
    }

    public Event update(Event event) {
        Object updatedEvent = bookingStore.update(PART_OF_ID + event.getId(), event);
        return event != null ? (Event) updatedEvent : null;
    }

    public boolean delete(Event event) {
        return event != null ? deleteById(event.getId()) : false;
    }

    public boolean deleteById(long id) {
        return bookingStore.delete(PART_OF_ID + id);
    }

    public List<Event> selectByTitle(final String title, final int pageSize, final int pageNum) {
        List<Event> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        //NoSuchelementEx
        return events.stream()
                .filter(e -> e.getTitle().equals(title))
                .skip(pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Event> selectForDay(Date day, int pageSize, int pageNum) {
       /* List<Event> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        //NoSuchelementEx
        return events.stream()
                .filter(e -> e.getTitle().equals(title))
                .skip(pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());*/
        return null;
    }
}
