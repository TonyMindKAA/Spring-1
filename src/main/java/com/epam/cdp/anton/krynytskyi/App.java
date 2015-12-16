package com.epam.cdp.anton.krynytskyi;

import com.epam.cdp.anton.krynytskyi.domain.facade.BookingFacadeImpl;
import com.epam.cdp.anton.krynytskyi.domain.io.BookingJSonIO;
import com.epam.cdp.anton.krynytskyi.domain.model.BookingJSonObj;
import com.epam.cdp.anton.krynytskyi.domain.model.EventBean;
import com.epam.cdp.anton.krynytskyi.domain.model.TicketBean;
import com.epam.cdp.anton.krynytskyi.domain.model.UserBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BookingFacadeImpl bookingFacadeImpl = context.getBean(BookingFacadeImpl.class);
        System.out.println(bookingFacadeImpl.deleteUser(1));
    }
}