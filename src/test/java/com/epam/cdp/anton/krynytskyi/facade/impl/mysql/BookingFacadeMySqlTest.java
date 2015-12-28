package com.epam.cdp.anton.krynytskyi.facade.impl.mysql;

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
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeMySqlTest {

    @InjectMocks
    private BookingFacadeMySql bookingFacadeMySql;




}
