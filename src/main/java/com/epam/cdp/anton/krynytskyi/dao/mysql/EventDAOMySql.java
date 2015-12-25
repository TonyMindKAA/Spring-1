package com.epam.cdp.anton.krynytskyi.dao.mysql;

import com.epam.cdp.anton.krynytskyi.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.mapers.EventRowMapper;
import com.epam.cdp.anton.krynytskyi.mapers.UserRowMapper;
import com.epam.cdp.anton.krynytskyi.model.Event;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventDAOMySql implements EventDAO {

    private static Logger LOG = Logger.getLogger("EventDAOMySql");

    public static final String SELECT_ALL_EVENTS = "select * from event";
    public static final String SELECT_EVENT_BY_ID = "select * from event where id = ?";
    public static final String INSERT_EVENT = "insert into event (title, ticket_price, date) values (?, ?)";
    public static final String UPDATE_EVENT = "update event set title = ?, ticket_price = ? date = ? where id = ?";
    public static final String DELETE_EVENT = "delete from event where id = ?";
    public static final String SELECT_EVENT_BY_TITLE = "select * from event where title = ? limit ?, ?";
    public static final String SELECT_EVENT_BY_DATE = "select * from event where name = ? limit ?, ?";

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
        return jdbcTemplateObject.query(SELECT_ALL_EVENTS, new EventRowMapper());
    }

    @Override
    public Event selectById(long id) {
        return jdbcTemplateObject.queryForObject(SELECT_EVENT_BY_ID,
                new Object[]{id},
                new EventRowMapper());
    }

    @Override
    public Event insert(Event event) {
        jdbcTemplateObject.update(INSERT_EVENT,
                event.getTitle(),
                event.getTicketPrice(),
                event.getDate());
        return event;
    }

    @Override
    public Event update(Event event) {
        jdbcTemplateObject.update(UPDATE_EVENT,
                event.getTitle(),
                event.getTicketPrice(),
                event.getDate(),
                event.getId());
        return event;
    }

    @Override
    public boolean delete(Event event) {
        return deleteById(event.getId());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplateObject.update(DELETE_EVENT, id) > 0 ? true : false;
    }

    @Override
    public List<Event> selectByTitle(String title, int pageSize, int pageNum) {
        int paginationSize = pageSize * (pageNum - 1);
        return jdbcTemplateObject.query(SELECT_EVENT_BY_TITLE,
                new Object[]{title,
                        paginationSize,
                        pageSize},
                new EventRowMapper());
    }

    @Override
    public List<Event> selectForDay(Date day, int pageSize, int pageNum) {
        int paginationSize = pageSize * (pageNum - 1);
        return jdbcTemplateObject.query(SELECT_EVENT_BY_DATE,
                new Object[]{day,
                        paginationSize,
                        pageSize},
                new EventRowMapper());
    }

    public String convertStringToDate(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateString = sdfr.format(indate);
        } catch (Exception ex) {
            LOG.error("Cann't convert date to string", ex);
        }
        return dateString;
    }
}
