package com.epam.cdp.anton.krynytskyi;

import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.dao.store.EventDAOStore;
import com.epam.cdp.anton.krynytskyi.domain.dao.store.TicketDAOStore;
import com.epam.cdp.anton.krynytskyi.domain.dao.store.UserDAOStore;
import com.epam.cdp.anton.krynytskyi.domain.facade.BookingFacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "Spring-Module.xml");

        BookingStore bookingStore = (BookingStore) context.getBean("storage");
        System.out.println(bookingStore.readAll().size());

        BookingFacadeImpl bookingFacadeImpl = (BookingFacadeImpl) context.getBean("bookingFacadeImpl");
        System.out.println(bookingFacadeImpl.deleteUser(1));

    }
}