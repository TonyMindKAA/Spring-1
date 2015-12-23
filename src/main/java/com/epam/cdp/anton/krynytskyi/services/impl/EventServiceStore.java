package com.epam.cdp.anton.krynytskyi.services.impl;

import com.epam.cdp.anton.krynytskyi.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class EventServiceStore implements EventService {
    @Autowired
    private EventDAO eventDAO;

    public List<Event> selectAll() {
        return eventDAO.selectAll();
    }

    public Event selectById(long id) {
        return eventDAO.selectById(id);
    }

    public Event insert(Event event) {
        return eventDAO.insert(event);
    }

    public Event update(Event event) {
        return eventDAO.update(event);
    }

    public boolean delete(Event event) {
        return eventDAO.delete(event);
    }

    public boolean deleteById(long id) {
        return eventDAO.deleteById(id);
    }

    public List<Event> selectByTitle(String title, int pageSize, int pageNum) {
        return eventDAO.selectByTitle(title, pageSize, pageNum);
    }

    public List<Event> selectForDay(Date day, int pageSize, int pageNum) {
        return eventDAO.selectForDay(day, pageSize, pageNum);
    }
}
