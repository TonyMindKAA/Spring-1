package com.epam.cdp.anton.krynytskyi.dao.mysql;

import com.epam.cdp.anton.krynytskyi.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.mapers.TicketRowMapper;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDAOMySql implements TicketDAO {

    public static final String SELECT_ALL_TICKETS = "select * from ticket";
    public static final String SELECT_TICKET_BY_ID = "select * from ticket where id = ?";
    public static final String INSERT_TICKET = "insert into ticket (category, eventId, place, userId) values (?, ?, ?, ?)";
    public static final String UPDATE_TICKET = "update ticket set category = ?, eventId = ?, place = ?, userId = ? where id = ?";
    public static final String DELETE_TICKET = "delete from ticket where id = ?";
    public static final String SELECT_TICKET_BY_USER = "select * from ticket where userId = ? limit ?, ?";
    public static final String SELECT_TICKET_BY_EVENT = "select * from ticket where eventId = ? limit ?, ?";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    private Map<Ticket.Category, String> categories = new HashMap<Ticket.Category, String>() {{
        put(Ticket.Category.BAR, "BAR");
        put(Ticket.Category.PREMIUM, "PREMIUM");
        put(Ticket.Category.STANDARD, "STANDARD");
    }};

    public TicketDAOMySql() {
    }

    @Autowired
    public TicketDAOMySql(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ticket> selectAll() {
        return jdbcTemplateObject.query(SELECT_ALL_TICKETS, new TicketRowMapper());
    }

    @Override
    public Ticket selectById(long id) {
        return jdbcTemplateObject.queryForObject(SELECT_TICKET_BY_ID,
                new Object[]{id},
                new TicketRowMapper());
    }

    @Override
    public Ticket insert(Ticket ticket) {
        jdbcTemplateObject.update(INSERT_TICKET,
                categories.get(ticket.getCategory()),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getUserId());
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        jdbcTemplateObject.update(UPDATE_TICKET,
                categories.get(ticket.getCategory()),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getUserId(),
                ticket.getId());
        return ticket;
    }

    @Override
    public boolean delete(Ticket ticket) {
        return deleteById(ticket.getId());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplateObject.update(DELETE_TICKET, id) > 0 ? true : false;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return insert(createTicketObj(userId, eventId, place, category));
    }

    private Ticket createTicketObj(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new TicketBean();
        ticket.setCategory(category);
        ticket.setPlace(place);
        ticket.setEventId(eventId);
        ticket.setUserId(userId);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        int paginationSize = pageSize * (pageNum - 1);
        return jdbcTemplateObject.query(SELECT_TICKET_BY_USER,
                new Object[]{user.getId(),
                        paginationSize,
                        pageSize},
                new TicketRowMapper());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        int paginationSize = pageSize * (pageNum - 1);
        return jdbcTemplateObject.query(SELECT_TICKET_BY_EVENT,
                new Object[]{event.getId(),
                        paginationSize,
                        pageSize},
                new TicketRowMapper());
    }
}
