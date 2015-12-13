package com.epam.cdp.anton.krynytskyi.api.store;

import com.epam.cdp.anton.krynytskyi.api.model.Event;

import java.util.List;

/**
 * @author Anton_Krynytskyi
 */
public interface BookingStore {

    List<Object> readAll();

    Object create(String key, Object value);

    Object read(String key);

    Object update(String key, Object value);

    boolean delete(String key);

    List<Event> readAll(String partOfId);
}
