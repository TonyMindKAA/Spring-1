package com.epam.cdp.anton.krynytskyi.dao.mysql;

import com.epam.cdp.anton.krynytskyi.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class EventDAOMySql implements EventDAO {

    public static final String SELECT_ALL_EVENTS = "select * from event";
    public static final String SELECT_EVENT_BY_ID = "select * from event where id = ?";
    public static final String INSERT_EVENT = "insert into event (name, email) values (?, ?)";
    public static final String UPDATE_EVENT = "update event set name = ?, email = ? where id = ?";
    public static final String DELETE_EVENT = "delete from event where id = ?";
    public static final String SELECT_EVENT_BY_EMAIL = "select * from event where email = ?";
    public static final String SELECT_EVENT_BY_NAME = "select * from event where name = ? limit ?, ?";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public EventDAOMySql() {
    }

    @Autowired
    public EventDAOMySql(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Event> selectAll() {
        return null;
    }

    @Override
    public Event selectById(long id) {
        return null;
    }

    @Override
    public Event insert(Event event) {
        return null;
    }

    @Override
    public Event update(Event event) {
        return null;
    }

    @Override
    public boolean delete(Event event) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public List<Event> selectByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public List<Event> selectForDay(Date day, int pageSize, int pageNum) {
        return null;
    }
}
