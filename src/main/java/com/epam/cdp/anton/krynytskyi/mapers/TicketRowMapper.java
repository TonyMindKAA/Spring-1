package com.epam.cdp.anton.krynytskyi.mapers;

import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class TicketRowMapper implements RowMapper<Ticket> {

    private static Logger LOG = Logger.getLogger("EventRowMapper");
    private Map<String, Ticket.Category> categories = new HashMap<String, Ticket.Category>() {{
        put("BAR", Ticket.Category.BAR);
        put("PREMIUM", Ticket.Category.PREMIUM);
        put("STANDARD", Ticket.Category.STANDARD);
    }};

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket event = new TicketBean();
        event.setId(rs.getInt("id"));
        event.setPlace(rs.getInt("place"));
        event.setEventId(rs.getInt("eventId"));
        event.setUserId(rs.getInt("userId"));
        event.setCategory(categories.get(rs.getString("category")));
        return event;
    }
}