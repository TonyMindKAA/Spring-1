package com.epam.cdp.anton.krynytskyi.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.dao.store.TicketDAOStore;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import com.epam.cdp.anton.krynytskyi.services.impl.TicketServiceStore;
import com.epam.cdp.anton.krynytskyi.store.impl.BookingStoreImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceStoreTest {

    @InjectMocks
    private TicketDAOStore ticketDAOStore;

    @InjectMocks
    private TicketServiceStore ticketServiceStore;

    @Before
    public void initialize() {
        ticketDAOStore.setBookingStore(new BookingStoreImpl());
        ticketServiceStore.setTicketDAO(ticketDAOStore);
    }

    @Test
    public void shoutReturnEmptyListOfEvents_whenInvokeSelectAll() {
        assertThat(ticketServiceStore.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shoutReturnListOfEventsWithThreeElements_whenInvokeSelectAll() {
        ticketServiceStore.insert(new TicketBean());
        ticketServiceStore.insert(new TicketBean());
        ticketServiceStore.insert(new TicketBean());

        assertEquals(ticketServiceStore.selectAll().size(), 3);

    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {
        Ticket ticket = ticketServiceStore.insert(new TicketBean());

        assertEquals(ticketServiceStore.selectById(ticket.getId()).getId(), ticket.getId());
    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {
        ticketServiceStore.insert(new TicketBean());

        int wrongId = 222222;
        assertThat(ticketServiceStore.selectById(wrongId)).isNull();

    }

    @Test
    public void shoutInsertOneElement_whenInvokeInsertMethod() {
        ticketServiceStore.insert(new TicketBean());

        assertEquals(ticketServiceStore.selectAll().size(), 1);
    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {
        Ticket updateObj = new TicketBean();
        Ticket ticket = ticketServiceStore.insert(new TicketBean());

        int place = 40;
        ticket.setPlace(place);
        updateObj.setId(ticket.getId());
        int place2 = 18;
        updateObj.setPlace(place2);

        Ticket previewValue = ticketServiceStore.update(updateObj);
        assertEquals(previewValue.getPlace(), place);

        Ticket newUpdatedEvent = ticketServiceStore.selectById(updateObj.getId());
        assertEquals(newUpdatedEvent.getPlace(), place2);

    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistTicket() {
        Ticket updateObj = new TicketBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(ticketServiceStore.update(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {

        Ticket newObject = ticketServiceStore.insert(new TicketBean());

        assertThat(ticketServiceStore.deleteById(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistTicket() {

        int notExistElement = 999999999;

        assertThat(ticketServiceStore.deleteById(notExistElement)).isFalse();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteMethod() {

        Ticket newObject = ticketServiceStore.insert(new TicketBean());

        assertThat(ticketServiceStore.delete(newObject)).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteNotExistEvent() {

        int notExistElement = 999999999;
        Ticket newObject = new TicketBean() {{
            setId(notExistElement);
        }};

        assertThat(ticketServiceStore.delete(newObject)).isFalse();
    }

    @Test
    public void shouldBookTicket_whenPutValidParams() {

        ticketServiceStore.bookTicket(586, 23L, 23, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.selectAll().size(), 1);
    }

    @Test
    public void shouldGetBookedTicketByUserThreeElement_whenPageSizeThreePageFirst() {

        ticketServiceStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 3, 1)
                             .size(), 3);
    }

    @Test
    public void shouldGetBookedTicketByUserZeroElement_whenPageSizeThreePageSecond() {

        ticketServiceStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 3, 2).size(), 0);
    }

    @Test
    public void shouldGetBookedTicketByUserOneElement_whenPageSizeTwoPageSecond() {

        ticketServiceStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 2, 2)
                             .size(), 1);
    }


    @Test
    public void shouldGetBookedTicketByEventThreeElement_whenPageSizeThreePageFirst() {

        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 3, 1).size(), 3);
    }

    @Test
    public void shouldGetBookedTicketByEventZeroElement_whenPageSizeThreePageSecond() {

        ticketServiceStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 3, 2).size(), 0);
    }

    @Test
    public void shouldGetBookedTicketByEventOneElement_whenPageSizeTwoPageSecond() {

        ticketServiceStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketServiceStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketServiceStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketServiceStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketServiceStore.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 2, 2).size(), 1);
    }

}
