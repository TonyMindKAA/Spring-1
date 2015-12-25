package com.epam.cdp.anton.krynytskyi.mapers;

import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventRowMapper implements RowMapper<Event> {

    private static Logger LOG = Logger.getLogger("EventRowMapper");

    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new EventBean();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setDate(getDate(rs.getString("date")));
        event.setTicketPrice(rs.getInt("ticket_price"));
        return event;
    }

    private Date getDate(String stringDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            LOG.error("Cann't parse date from string!",e);
        }
        return null;
    }
}