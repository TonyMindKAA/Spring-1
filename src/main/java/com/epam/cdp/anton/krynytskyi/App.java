package com.epam.cdp.anton.krynytskyi;

import com.epam.cdp.anton.krynytskyi.facade.impl.store.BookingFacadeStore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BookingFacadeStore bookingFacadeStore = context.getBean(BookingFacadeStore.class);
        System.out.println(bookingFacadeStore.deleteUser(1));
    }
}