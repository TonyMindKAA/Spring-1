package com.epam.cdp.anton.krynytskyi.store;

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

    List<Object> readAll(String partOfId);
}
