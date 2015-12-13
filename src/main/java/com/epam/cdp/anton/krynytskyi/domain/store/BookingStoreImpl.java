package com.epam.cdp.anton.krynytskyi.domain.store;

import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.api.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.setter.id.SetterIdEventBean;
import com.epam.cdp.anton.krynytskyi.domain.setter.id.SetterIdTicketBean;
import com.epam.cdp.anton.krynytskyi.domain.setter.id.SetterIdUserBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BookingStoreImpl implements BookingStore {

    private Map<String, Object> store = new HashMap<String, Object>();
    private Set<Long> indexSet = new HashSet<Long>();
    private Map<String, SetterId> casterMap = new HashMap<String, SetterId>() {
        {
            put("EventBean", new SetterIdEventBean());
            put("TicketBean", new SetterIdTicketBean());
            put("UserBean", new SetterIdUserBean());
        }
    };

    public List<Object> readAll() {
        return new ArrayList<Object>(Arrays.asList(indexSet.toArray()));
    }

    public Object create(String key, Object value) {
        long newId = generateId();
        Object objWithId = setIdToObject(key, value, newId);
        if (objWithId != null) {
            store.put(key + newId, objWithId);
        }
        return store.get(key);
    }

    public Object setIdToObject(String key, Object obj, long id) {
        SetterId caster = casterMap.get(key);
        return caster != null ? caster.setId(obj, id) : null;
    }

    public Object read(String key) {
        return store.get(key);
    }

    public Object update(String key, Object value) {
        Object previousVersion = store.get(key);
        if (previousVersion != null) {
            store.put(key, value);
        }
        return previousVersion;
    }

    public boolean delete(String key) {
        long id = partId(key);
        if (id > -1 && indexSet.contains(id)) {
            store.remove(key);
            indexSet.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Event> readAll(String partOfId) {
        return null;
    }

    private long partId(String key) {
        String[] keyParts = key.split(":");
        return keyParts.length == 2 ? stringToLong(keyParts[1]) : -1;
    }

    private long stringToLong(String keyPart) {
        try {
            return Long.parseLong(keyPart);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private long generateId() {
        Random indexGenerator = new Random();
        while (true) {
            long x = processIndex(indexGenerator.nextLong());
            if (x != -1) {
                return x;
            }
        }
    }

    private long processIndex(long x) {
        if (x > -1 && !indexSet.contains(x)) {
            return x;
        } else if (!indexSet.contains(x)) {
            return (x * -1);
        }
        return -1;
    }
}
