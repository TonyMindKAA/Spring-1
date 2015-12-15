package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import static com.epam.cdp.anton.krynytskyi.domain.model.Const.EVENT_BEAN;

import com.epam.cdp.anton.krynytskyi.api.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.model.Const;

import java.util.*;
import java.util.stream.Collectors;

public class EventDAOStore implements EventDAO {

    public static final String PART_OF_ID = "event:";
    private BookingStore bookingStore;

    public void setBookingStore(BookingStore bookingStore) {
        this.bookingStore = bookingStore;
    }

    public List<Event> selectAll() {
        List<Object> objectList = bookingStore.readAll(EVENT_BEAN);
        List<Event> eventList = new ArrayList<>();

        objectList.stream()
                .filter(elt -> elt != null)
                .forEach(elt -> eventList.add(castToEvent(elt)));

        return eventList;
    }

    private Event castToEvent(Object elt) {
        return ((Event) elt);
    }

    public Event selectById(long id) {
        Object read = bookingStore.read(PART_OF_ID + id);
        return read != null ? (Event) read : null;
    }

    public Event insert(Event event) {
        Object insertedEvent = bookingStore.create(EVENT_BEAN, event);
        return event != null ? (Event) insertedEvent : null;
    }

    public Event update(Event event) {
        Object updatedEvent = bookingStore.update(EVENT_BEAN + ":" + event.getId(), event);
        return event != null ? (Event) updatedEvent : null;
    }

    public boolean delete(Event event) {
        return event != null ? deleteById(event.getId()) : false;
    }

    public boolean deleteById(long id) {
        return bookingStore.delete(EVENT_BEAN + ":" + id);
    }

    public List<Event> selectByTitle(final String title, final int pageSize, final int pageNum) {
        List<Event> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return events.stream()
                .filter(e -> e.getTitle().equals(title))
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Event> selectForDay(Date day, int pageSize, int pageNum) {
        List<Event> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return events.stream()
                .filter(e -> compareDateWithoutTime(e.getDate(), day))
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    private boolean compareDateWithoutTime(Date dateOne, Date dateTwo) {
        Date oneWithoutTime = removeTime(new Date(dateOne.getTime()));
        Date twoWithoutTime = removeTime(new Date(dateTwo.getTime()));
        return oneWithoutTime.equals(twoWithoutTime);
    }

    private Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
