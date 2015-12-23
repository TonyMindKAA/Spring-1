package com.epam.cdp.anton.krynytskyi.dao.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.epam.cdp.anton.krynytskyi.dao.store.TicketDAOStore;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import com.epam.cdp.anton.krynytskyi.store.impl.BookingStoreImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class TicketDAOStoreTest {

    private TicketDAOStore ticketDAOStore;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        ticketDAOStore = context.getBean(TicketDAOStore.class);
    }

    @Test
    public void shoutReturnEmptyListOfEvents_whenInvokeSelectAll() {
        assertThat(ticketDAOStore.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shoutReturnListOfEventsWithThreeElements_whenInvokeSelectAll() {
        ticketDAOStore.insert(new TicketBean());
        ticketDAOStore.insert(new TicketBean());
        ticketDAOStore.insert(new TicketBean());

        assertEquals(ticketDAOStore.selectAll().size(), 3);

    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {
        Ticket ticket = ticketDAOStore.insert(new TicketBean());

        assertEquals(ticketDAOStore.selectById(ticket.getId()).getId(), ticket.getId());

    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {
        ticketDAOStore.insert(new TicketBean());

        int wrongId = 222222;
        assertThat(ticketDAOStore.selectById(wrongId)).isNull();

    }

    @Test
    public void shoutInsertOneElement_whenInvokeInsertMethod() {
        ticketDAOStore.insert(new TicketBean());

        assertEquals(ticketDAOStore.selectAll().size(), 1);

    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {
        Ticket updateObj = new TicketBean();
        Ticket ticket = ticketDAOStore.insert(new TicketBean());

        int place = 40;
        ticket.setPlace(place);
        updateObj.setId(ticket.getId());
        int place2 = 18;
        updateObj.setPlace(place2);

        Ticket previewValue = ticketDAOStore.update(updateObj);
        assertEquals(previewValue.getPlace(), place);

        Ticket newUpdatedEvent = ticketDAOStore.selectById(updateObj.getId());
        assertEquals(newUpdatedEvent.getPlace(), place2);

    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistEvent() {
        Ticket updateObj = new TicketBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(ticketDAOStore.update(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {
        Ticket newObject = ticketDAOStore.insert(new TicketBean());

        assertThat(ticketDAOStore.deleteById(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistEvent() {
        int notExistElement = 999999999;

        assertThat(ticketDAOStore.deleteById(notExistElement)).isFalse();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteMethod() {
        Ticket newObject = ticketDAOStore.insert(new TicketBean());

        assertThat(ticketDAOStore.delete(newObject)).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteNotExistEvent() {
        int notExistElement = 999999999;
        Ticket newObject = new TicketBean() {{
            setId(notExistElement);
        }};

        assertThat(ticketDAOStore.delete(newObject)).isFalse();
    }

    @Test
    public void shouldBookTicket_whenPutValidParams() {
        ticketDAOStore.bookTicket(586, 23L, 23, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.selectAll().size(), 1);
    }

    @Test
    public void shouldGetBookedTicketByUserThreeElement_whenPageSizeThreePageFirst() {
        ticketDAOStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 3, 1)
                .size(), 3);
    }

    @Test
    public void shouldGetBookedTicketByUserZeroElement_whenPageSizeThreePageSecond() {
        ticketDAOStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 3, 2).size(), 0);
    }

    @Test
    public void shouldGetBookedTicketByUserOneElement_whenPageSizeTwoPageSecond() {
        ticketDAOStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 2, 2)
                .size(), 1);
    }


    @Test
    public void shouldGetBookedTicketByEventThreeElement_whenPageSizeThreePageFirst() {
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 3, 1).size(), 3);
    }

    @Test
    public void shouldGetBookedTicketByEventZeroElement_whenPageSizeThreePageSecond() {
        ticketDAOStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 3, 2).size(), 0);
    }

    @Test
    public void shouldGetBookedTicketByEventOneElement_whenPageSizeTwoPageSecond() {


        ticketDAOStore.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        ticketDAOStore.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        ticketDAOStore.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        ticketDAOStore.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(ticketDAOStore.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 2, 2).size(), 1);
    }

}
