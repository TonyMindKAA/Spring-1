package com.epam.cdp.anton.krynytskyi.facade.impl;

import static org.junit.Assert.assertEquals;

import com.epam.cdp.anton.krynytskyi.facade.impl.BookingFacadeImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeImplPostProcessorTest {

    private BookingFacadeImpl bookingFacade;

    @Before
    public void initialize() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        bookingFacade = context.getBean(BookingFacadeImpl.class);
    }

    @Ignore
    @Test
    public void shouldReturnSizeTwentyFour_whenInvokeMethodSize() {
        int expected = 24;
        int actual = bookingFacade.size();
        assertEquals(expected, actual);
    }
}
