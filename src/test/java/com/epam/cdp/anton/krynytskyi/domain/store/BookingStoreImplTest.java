package com.epam.cdp.anton.krynytskyi.domain.store;

import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.model.EventBean;
import com.epam.cdp.anton.krynytskyi.domain.model.TicketBean;
import com.epam.cdp.anton.krynytskyi.domain.model.UserBean;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookingStoreImplTest {
    private BookingStore bookingStore = new BookingStoreImpl();

    @Test
    public void shouldReturnEmptyList_whenBookingStoreObjectJustInstances() {
        BookingStore localBookingStore = new BookingStoreImpl();
        List<Object> objects = localBookingStore.readAll();
        int actual = objects.size();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnListWithOneElement_whenBookingStoreObjectContainOneElement() {
        bookingStore.create("EventBean", new EventBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAddNewEvent_whenPutEventObjToCreateMethod() {
        bookingStore.create("EventBean", new EventBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
        assertTrue(objects.get(0) instanceof EventBean);
    }

    @Test
    public void shouldAddNewTicket_whenPutTicketObjToCreateMethod() {
        bookingStore.create("TicketBean", new TicketBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
        assertTrue(objects.get(0) instanceof TicketBean);
    }

    @Test
    public void shouldAddNewUser_whenPutUserObjToCreateMethod() {
        bookingStore.create("UserBean", new UserBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
        assertTrue(objects.get(0) instanceof UserBean);
    }

    @Test
    public void shouldAddNotAddOtherObjectWithoutUserEventTicketKeysAndReturn_whenPutSomeKeyAndSomeObject() {
        bookingStore.create("SomeKey", new UserBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReadTecket_whenBookingStoreContainTicketWithSameId() {
        TicketBean actual = (TicketBean) bookingStore.create("TicketBean", new TicketBean());
        TicketBean expected = (TicketBean) bookingStore.read("TicketBean:" + actual.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldReadUser_whenBookingStoreContainUserWithSameId() {
        UserBean actual = (UserBean) bookingStore.create("UserBean", new UserBean());
        UserBean expected = (UserBean) bookingStore.read("UserBean:" + actual.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldReadEvent_whenBookingStoreContainEventWithSameId() {
        EventBean actual = (EventBean) bookingStore.create("EventBean", new EventBean());
        EventBean expected = (EventBean) bookingStore.read("EventBean:" + actual.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldDeleteEvent_whenBookingStoreContainEventWithSameId() {
        EventBean actual = (EventBean) bookingStore.create("EventBean", new EventBean());
        assertTrue(bookingStore.delete("EventBean:" + actual.getId()));
        assertNull(bookingStore.read("EventBean:" + actual.getId()));
    }

    @Test
    public void shouldDeleteTicket_whenBookingStoreContainTicketWithSameId() {
        TicketBean actual = (TicketBean) bookingStore.create("TicketBean", new TicketBean());
        assertTrue(bookingStore.delete("EventBean:" + actual.getId()));
        assertNull(bookingStore.read("EventBean:" + actual.getId()));
    }

    @Test
    public void shouldDeleteUser_whenBookingStoreContainUserWithSameId() {
        UserBean actual = (UserBean) bookingStore.create("UserBean", new UserBean());
        assertTrue(bookingStore.delete("UserBean:" + actual.getId()));
        assertNull(bookingStore.read("UserBean:" + actual.getId()));
    }

    @Test
    public void shouldUpdateUser_whenBookingStoreContainUser() {
        UserBean actual = (UserBean) bookingStore.create("UserBean", new UserBean());
        actual.setName("Bob");
        UserBean expected = (UserBean) bookingStore.update("UserBean:" + actual.getId(), actual);
        assertEquals(expected.getId(), actual.getId());
        assertTrue(expected.getName().equals(actual.getName()));
    }

    @Test
    public void shouldUpdateEvent_whenBookingStoreContainEvent() {
        EventBean actual = (EventBean) bookingStore.create("EventBean", new EventBean());
        actual.setTitle("Bob");
        EventBean expected = (EventBean) bookingStore.update("EventBean:" + actual.getId(), actual);
        assertEquals(expected.getId(), actual.getId());
        assertTrue(expected.getTitle().equals(actual.getTitle()));
    }

    @Test
    public void shouldUpdateTicket_whenBookingStoreContainTicket() {
        TicketBean actual = (TicketBean) bookingStore.create("TicketBean", new TicketBean());
        actual.setPlace(25);
        TicketBean expected = (TicketBean) bookingStore.update("TicketBean:" + actual.getId(), actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPlace(), actual.getPlace());
    }

    @Test
    public void shouldNoUpdateTicketRturnNull_whenBookingStoreNoContainTicketWithTheSameId() {
        TicketBean actual = (TicketBean) bookingStore.create("TicketBean", new TicketBean());
        assertNull(bookingStore.update("TicketBean:111111111", actual));
    }
}
