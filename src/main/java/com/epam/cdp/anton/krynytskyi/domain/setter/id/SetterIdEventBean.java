package com.epam.cdp.anton.krynytskyi.domain.setter.id;

import com.epam.cdp.anton.krynytskyi.api.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.domain.model.EventBean;

public class SetterIdEventBean implements SetterId<EventBean> {

    public EventBean setId(Object obj, long id) {
        EventBean event = ((EventBean) obj);
        event.setId(id);
        return event;
    }
}

