package com.epam.cdp.anton.krynytskyi.domain.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.epam.cdp.anton.krynytskyi.api.facade.BookingFacade;
import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.model.EventBean;
import com.epam.cdp.anton.krynytskyi.domain.store.BookingStoreImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeImplTest {

    private BookingFacade bookingFacade;

    @Before
    public void initialize() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        bookingFacade = context.getBean(BookingFacadeImpl.class);

    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {
        Event newEvent = bookingFacade.createEvent(new EventBean());

        assertEquals(bookingFacade.getEventById(newEvent.getId()).getId(), newEvent.getId());
    }
}
