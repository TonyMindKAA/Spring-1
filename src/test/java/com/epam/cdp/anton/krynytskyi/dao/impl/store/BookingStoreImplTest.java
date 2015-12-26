package com.epam.cdp.anton.krynytskyi.dao.impl.store;

import static com.epam.cdp.anton.krynytskyi.model.Const.EVENT_BEAN;
import static com.epam.cdp.anton.krynytskyi.model.Const.TICKET_BEAN;
import static com.epam.cdp.anton.krynytskyi.model.Const.USER_BEAN;
import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.cdp.anton.krynytskyi.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.store.impl.BookingStoreImpl;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class BookingStoreImplTest {

    @InjectMocks
    private BookingStoreImpl bookingStore;

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
        bookingStore.create(EVENT_BEAN, new EventBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAddNewEvent_whenPutEventObjToCreateMethod() {
        bookingStore.create(EVENT_BEAN, new EventBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
        assertTrue(objects.get(0) instanceof EventBean);
    }

    @Test
    public void shouldAddNewTicket_whenPutTicketObjToCreateMethod() {
        bookingStore.create(TICKET_BEAN, new TicketBean());
        List<Object> objects = bookingStore.readAll();
        int actual = objects.size();
        int expected = 1;
        assertEquals(expected, actual);
        assertTrue(objects.get(0) instanceof TicketBean);
    }

    @Test
    public void shouldAddNewUser_whenPutUserObjToCreateMethod() {
        bookingStore.create(USER_BEAN, new UserBean());
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
        TicketBean actual = (TicketBean) bookingStore.create(TICKET_BEAN, new TicketBean());
        TicketBean expected = (TicketBean) bookingStore.read(TICKET_BEAN + ":" + actual.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldReadUser_whenBookingStoreContainUserWithSameId() {
        UserBean actual = (UserBean) bookingStore.create(USER_BEAN, new UserBean());
        UserBean expected = (UserBean) bookingStore.read(USER_BEAN + ":" + actual.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldReadEvent_whenBookingStoreContainEventWithSameId() {
        EventBean actual = (EventBean) bookingStore.create(EVENT_BEAN, new EventBean());
        EventBean expected = (EventBean) bookingStore.read(EVENT_BEAN + ":" + actual.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void shouldDeleteEvent_whenBookingStoreContainEventWithSameId() {
        EventBean actual = (EventBean) bookingStore.create(EVENT_BEAN, new EventBean());
        assertThat(bookingStore.delete(EVENT_BEAN +":" + actual.getId())).isTrue();
        assertThat(bookingStore.read(EVENT_BEAN+":" + actual.getId())).isNull();
    }

    @Test
    public void shouldDeleteTicket_whenBookingStoreContainTicketWithSameId() {
        TicketBean actual = (TicketBean) bookingStore.create(TICKET_BEAN, new TicketBean());
        assertTrue(bookingStore.delete(TICKET_BEAN + ":" + actual.getId()));
        assertThat(bookingStore.read(TICKET_BEAN+":" + actual.getId())).isNull();
    }

    @Test
    public void shouldDeleteUser_whenBookingStoreContainUserWithSameId() {
        UserBean actual = (UserBean) bookingStore.create(USER_BEAN, new UserBean());
        assertTrue(bookingStore.delete(USER_BEAN+":" + actual.getId()));
        assertThat(bookingStore.read(USER_BEAN+":" + actual.getId())).isNull();
    }

    @Test
    public void shouldReturnFalse_whenPutNotCorrectId() {
        assertFalse(bookingStore.delete(USER_BEAN));
    }

    @Test
    public void shouldUpdateUser_whenBookingStoreContainUser() {
        UserBean actual = (UserBean) bookingStore.create(USER_BEAN, new UserBean());
        actual.setName("Bob");
        UserBean expected = (UserBean) bookingStore.update(USER_BEAN+":" + actual.getId(), actual);
        assertEquals(expected.getId(), actual.getId());
        assertTrue(expected.getName().equals(actual.getName()));
    }

    @Test
    public void shouldUpdateEvent_whenBookingStoreContainEvent() {
        EventBean actual = (EventBean) bookingStore.create(EVENT_BEAN, new EventBean());
        actual.setTitle("Bob");
        EventBean expected = (EventBean) bookingStore.update(EVENT_BEAN+":" + actual.getId(), actual);
        assertEquals(expected.getId(), actual.getId());
        assertTrue(expected.getTitle().equals(actual.getTitle()));
    }

    @Test
    public void shouldUpdateTicket_whenBookingStoreContainTicket() {
        TicketBean actual = (TicketBean) bookingStore.create(TICKET_BEAN, new TicketBean());
        actual.setPlace(25);
        TicketBean
                expected =
                (TicketBean) bookingStore.update(TICKET_BEAN+":" + actual.getId(), actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPlace(), actual.getPlace());
    }

    @Test
    public void shouldNoUpdateTicketReturnNull_whenBookingStoreNoContainTicketWithTheSameId() {
        TicketBean actual = (TicketBean) bookingStore.create(TICKET_BEAN, new TicketBean());
        assertThat(bookingStore.update(TICKET_BEAN+":111111111", actual)).isNull();
    }

    @Test
    public void shouldReturnThreeUsers_whenStoreContainSevenElements() {
        BookingStore localBookingStore = new BookingStoreImpl();
        localBookingStore.create(USER_BEAN, new UserBean());
        localBookingStore.create(USER_BEAN, new UserBean());
        localBookingStore.create(USER_BEAN, new UserBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());

        int expected = 3;
        int actual = localBookingStore.readAll(USER_BEAN).size();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyList_whenStoreContainWithoutEventsSevenElementsAndReadAllEvents() {
        BookingStore localBookingStore = new BookingStoreImpl();
        localBookingStore.create(USER_BEAN, new UserBean());
        localBookingStore.create(USER_BEAN, new UserBean());
        localBookingStore.create(USER_BEAN, new UserBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());
        localBookingStore.create(TICKET_BEAN, new TicketBean());

        assertTrue(localBookingStore.readAll(EVENT_BEAN).isEmpty());
    }
}
