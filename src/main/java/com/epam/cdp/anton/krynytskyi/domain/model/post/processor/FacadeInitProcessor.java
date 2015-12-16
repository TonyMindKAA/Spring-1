package com.epam.cdp.anton.krynytskyi.domain.model.post.processor;

import com.epam.cdp.anton.krynytskyi.domain.facade.BookingFacadeImpl;
import com.epam.cdp.anton.krynytskyi.domain.io.BookingJSonIO;
import com.epam.cdp.anton.krynytskyi.domain.model.BookingJSonObj;
import com.epam.cdp.anton.krynytskyi.domain.model.EventBean;
import com.epam.cdp.anton.krynytskyi.domain.model.TicketBean;
import com.epam.cdp.anton.krynytskyi.domain.model.UserBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.File;
import java.util.List;

public class FacadeInitProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("bookingFacadeImpl")) {
            BookingFacadeImpl bookingFacade = (BookingFacadeImpl)bean;
            BookingJSonIO bookingJSonIO = new BookingJSonIO();
            BookingJSonObj obj = bookingJSonIO.read(new File("store.txt"));

            setValueToStore(bookingFacade, obj);
        }
        return bean;
    }

    private void setValueToStore(BookingFacadeImpl bookingFacade, BookingJSonObj obj) {
        List<EventBean> events = obj.getEvents();
        events.stream().forEach(val -> bookingFacade.createEvent(val));

        List<UserBean> users = obj.getUser();
        users.stream().forEach(val -> bookingFacade.createUser(val));

        List<TicketBean> tickets = obj.getTickets();
        tickets.stream().forEach(val -> bookingFacade.bookTicket(val.getUserId(),
                                                                 val.getEventId(),
                                                                 val.getPlace(),
                                                                 val.getCategory())
        );
    }
}
