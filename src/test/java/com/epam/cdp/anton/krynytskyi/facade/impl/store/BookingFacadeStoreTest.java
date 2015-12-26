package com.epam.cdp.anton.krynytskyi.facade.impl.store;

import com.epam.cdp.anton.krynytskyi.facade.BookingFacade;
import com.epam.cdp.anton.krynytskyi.facade.impl.store.BookingFacadeStore;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeStoreTest {

    private BookingFacade bookingFacade;

    @Before
    public void initialize() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        bookingFacade = context.getBean(BookingFacadeStore.class);
    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {
        Event newEvent = bookingFacade.createEvent(new EventBean());

        assertEquals(bookingFacade.getEventById(newEvent.getId()).getId(), newEvent.getId());
    }

    @Test
    public void shoutInvokeGetEventByIdReturnNull_whenPutWrongId() {
        int wrongId = 222222;
        assertThat(bookingFacade.getEventById(wrongId)).isNull();
    }

    @Test
    public void shoutInsertEvent_whenInvokeCreateEventMethod() {

        Event newEvent = bookingFacade.createEvent(new EventBean());

        assertEquals(bookingFacade.getEventById(newEvent.getId()).getId(), newEvent.getId());

    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateEventMethod() {

        Event updateObj = new EventBean();
        Event newEvent = bookingFacade.createEvent(new EventBean());

        String title = "test-tittle1";
        newEvent.setTitle(title);
        updateObj.setId(newEvent.getId());
        String title2 = "test-tittle2";
        updateObj.setTitle(title2);

        Event previewValue = bookingFacade.updateEvent(updateObj);
        assertThat(previewValue.getTitle().equals(title)).isTrue();

        Event newUpdatedEvent = bookingFacade.getEventById(updateObj.getId());
        assertThat(newUpdatedEvent.getTitle().equals(title2)).isTrue();
    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistEvent() {

        Event updateObj = new EventBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(bookingFacade.updateEvent(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokeDeleteByIdMethod() {

        Event newObject = bookingFacade.createEvent(new EventBean());

        assertThat(bookingFacade.deleteEvent(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistEvent() {

        int notExistElement = 999999999;

        assertThat(bookingFacade.deleteEvent(notExistElement)).isFalse();
    }

    @Test
    public void shoutReturnThreeEventsWithSomeTittle_whenInvokeSelectByTittle() {

        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(bookingFacade.getEventsByTitle("t2", 3, 1).size(), 3);
    }

    @Test
    public void shoutReturnZeroEventsWithSomeTittle_whenInvokeSelectByTittlePageSizeThreePageSecond() {

        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(bookingFacade.getEventsByTitle("t2", 3, 2).size(), 0);
    }

    @Test
    public void shoutReturnOneEventsWithSomeTittle_whenInvokeSelectByTittlePageSizeTwoPageSecond() {

        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t1");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});
        bookingFacade.createEvent(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(bookingFacade.getEventsByTitle("t2", 2, 2).size(), 1);
    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeThreePageFirst() {

        long beforeDec_15_2015 = 1450055646943L;
        long dec_15_2015 = 1450188646943L;
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(bookingFacade.getEventsForDay(new Date(beforeDec_15_2015), 3, 1).size(), 3);

    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeThreePageSecond() {

        long beforeDec_15_2015 = 1450055646943L;
        long dec_15_2015 = 1450188646943L;
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(bookingFacade.getEventsForDay(new Date(beforeDec_15_2015), 3, 2).size(), 0);

    }

    @Test
    public void shoutReturnOneEventForDay_whenInvokeSelectForDayMethodWithPageSizeTwoPageSecond() {

        long beforeDec_15_2015 = 1450055646943L;
        long dec_15_2015 = 1450188646943L;
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        bookingFacade.createEvent(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(bookingFacade.getEventsForDay(new Date(beforeDec_15_2015), 2, 2).size(), 1);
    }


    @Test
    public void shouldGetBookedTicketByUserThreeElement_whenPageSizeThreePageFirst() {

        bookingFacade.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        bookingFacade.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        bookingFacade.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(bookingFacade.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 3, 1)
                .size(), 3);
    }

    @Test
    public void shouldGetBookedTicketByUserZeroElement_whenPageSizeThreePageSecond() {

        bookingFacade.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        bookingFacade.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        bookingFacade.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(bookingFacade.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 3, 2).size(), 0);
    }

    @Test
    public void shouldGetBookedTicketByUserOneElement_whenPageSizeTwoPageSecond() {

        bookingFacade.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        bookingFacade.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        bookingFacade.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(bookingFacade.getBookedTickets(new UserBean() {{
            setId(555L);
        }}, 2, 2)
                .size(), 1);
    }


    @Test
    public void shouldGetBookedTicketByEventThreeElement_whenPageSizeThreePageFirst() {

        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.BAR);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        bookingFacade.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(bookingFacade.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 3, 1).size(), 3);
    }

    @Test
    public void shouldGetBookedTicketByEventZeroElement_whenPageSizeThreePageSecond() {

        bookingFacade.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        bookingFacade.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        bookingFacade.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(bookingFacade.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 3, 2).size(), 0);
    }

    @Test
    public void shouldGetBookedTicketByEventOneElement_whenPageSizeTwoPageSecond() {

        bookingFacade.bookTicket(444L, 18L, 23, Ticket.Category.BAR);
        bookingFacade.bookTicket(444L, 23L, 23, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(444L, 15L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(444L, 21L, 23, Ticket.Category.STANDARD);
        bookingFacade.bookTicket(555L, 20L, 18, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(555L, 20L, 35, Ticket.Category.BAR);
        bookingFacade.bookTicket(555L, 20L, 21, Ticket.Category.PREMIUM);

        assertEquals(bookingFacade.getBookedTickets(new EventBean() {{
            setId(20L);
        }}, 2, 2).size(), 1);
    }

    @Test
    public void shoutReturnUserById_whenInvokeSelectById() {

        User ticket = bookingFacade.createUser(new UserBean());

        assertEquals(bookingFacade.getUserById(ticket.getId()).getId(), ticket.getId());

    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {
        int wrongId = 222222;
        assertThat(bookingFacade.getUserById(wrongId)).isNull();

    }


    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {

        User updateObj = new UserBean();
        User user = bookingFacade.createUser(new UserBean());

        String name = "Bob";
        user.setName(name);
        updateObj.setId(user.getId());
        String name2 = "Bob1";
        updateObj.setName(name2);

        User previewValue = bookingFacade.updateUser(updateObj);
        assertEquals(previewValue.getName(), name);

        User newUpdatedEvent = bookingFacade.getUserById(updateObj.getId());
        assertEquals(newUpdatedEvent.getName(), name2);

    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistUser() {

        User updateObj = new UserBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(bookingFacade.updateUser(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {

        User newObject = bookingFacade.createUser(new UserBean());

        assertThat(bookingFacade.deleteUser(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistUser() {

        int notExistElement = 999999999;

        assertThat(bookingFacade.deleteUser(notExistElement)).isFalse();
    }


    @Test
    public void shouldReturnUserByEmail_whenEmailExist() {

        bookingFacade.createUser(new UserBean() {{
            setEmail("bob1@email.com");
        }});
        bookingFacade.createUser(new UserBean() {{
            setEmail("bob2@email.com");
        }});
        bookingFacade.createUser(new UserBean() {{
            setEmail("bob3@email.com");
        }});
        bookingFacade.createUser(new UserBean() {{
            setEmail("bob4@email.com");
        }});

        User user = bookingFacade.getUserByEmail("bob4@email.com");
        assertTrue(user.getEmail().equals("bob4@email.com"));
    }

    @Test
    public void shouldReturnNull_whenEmailUserWithEmailNotExist() {

        bookingFacade.createUser(new UserBean() {{
            setEmail("bob1@email.com");
        }});
        bookingFacade.createUser(new UserBean() {{
            setEmail("bob2@email.com");
        }});
        bookingFacade.createUser(new UserBean() {{
            setEmail("bob3@email.com");
        }});
        bookingFacade.createUser(new UserBean() {{
            setEmail("bob4@email.com");
        }});

        assertThat(bookingFacade.getUserByEmail("bob777@email.com")).isNull();
    }

    @Test
    public void shouldReturnThreeUsersByName_whenPageSizeThreePageFirs() {

        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(bookingFacade.getUsersByName("Bob_1", 3, 1).size(), 3);
    }

    @Test
    public void shouldReturnZeroUsersByName_whenPageSizeThreePageSecond() {

        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(bookingFacade.getUsersByName("Bob_1", 3, 2).size(), 0);
    }

    @Test
    public void shouldReturnOneUsersByName_whenPageSizeTwoPageSecond() {

        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(bookingFacade.getUsersByName("Bob_1", 2, 2).size(), 1);
    }

    @Test
    public void shouldReturnZeroUsersByName_whenPageSizeTwoPageSecondAndNotExistUserWithSomeName() {

        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_1");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});
        bookingFacade.createUser(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(bookingFacade.getUsersByName("Ivan", 2, 2).size(), 0);
    }

}
