package com.epam.cdp.anton.krynytskyi;

import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.facade.BookingFacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BookingStore bookingStore = context.getBean(BookingStore.class);
        System.out.println(bookingStore.readAll().size());
        BookingFacadeImpl bookingFacadeImpl = context.getBean(BookingFacadeImpl.class);
        System.out.println(bookingFacadeImpl.deleteUser(1));
        System.out.println(new Date().getTime());
    }
}