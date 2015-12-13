package com.epam.cdp.anton.krynytskyi.api.services;


import com.epam.cdp.anton.krynytskyi.api.model.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    List<Event> selectAll();

    Event selectById(long id);

    Event insert(Event event);

    Event update(Event event);

    boolean delete(Event event);

    boolean deleteById(long id);

    List<Event> selectByTitle(String title, int pageSize, int pageNum);

    List<Event> selectForDay(Date day, int pageSize, int pageNum);
}
