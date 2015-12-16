package com.epam.cdp.anton.krynytskyi;

import com.epam.cdp.anton.krynytskyi.domain.facade.BookingFacadeImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BookingFacadeImpl bookingFacadeImpl = context.getBean(BookingFacadeImpl.class);
        System.out.println(bookingFacadeImpl.deleteUser(1));
    }
}