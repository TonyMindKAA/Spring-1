package com.epam.cdp.anton.krynytskyi.domain.setter.id;

import com.epam.cdp.anton.krynytskyi.api.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.domain.model.TicketBean;

public class SetterIdTicketBean implements SetterId<TicketBean> {

    public TicketBean setId(Object obj, long id) {
        TicketBean ticket = (TicketBean) obj;
        ticket.setId(id);
        return ticket;
    }
}
