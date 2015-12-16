package com.epam.cdp.anton.krynytskyi.store.impl;

import static com.epam.cdp.anton.krynytskyi.model.Const.EVENT_BEAN;
import static com.epam.cdp.anton.krynytskyi.model.Const.TICKET_BEAN;
import static com.epam.cdp.anton.krynytskyi.model.Const.USER_BEAN;
import static java.util.Objects.nonNull;

import com.epam.cdp.anton.krynytskyi.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.model.AbstractBean;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import com.epam.cdp.anton.krynytskyi.setter.id.SetterIdBean;
import com.google.common.collect.Lists;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class BookingStoreImpl implements BookingStore {

    private static Logger LOG = Logger.getLogger("BookingStoreImpl");
    private Map<String, Object> store = new HashMap<>();
    private Set<Long> indexes = new HashSet<>();
    private Map<String, SetterId<AbstractBean>> setterIdMap = new HashMap<String, SetterId<AbstractBean>>() {
        {
            put(EVENT_BEAN, new SetterIdBean<EventBean>());
            put(TICKET_BEAN, new SetterIdBean<TicketBean>());
            put(USER_BEAN, new SetterIdBean<UserBean>());
        }
    };

    public List<Object> readAll() {
        return new ArrayList<>(Arrays.asList((store.values().toArray())));
    }

    public Object create(String key, Object value) {
        long newId = generateId();
        Object objWithId = setIdToObject(key, value, newId);
        if (nonNull(objWithId)) {
            indexes.add(newId);
            store.put(key + ":" + newId, objWithId);
        } else {
            LOG.debug("Didn't add to store object.");
        }
        return store.get(key + ":" + newId);
    }

    private Object setIdToObject(String key, Object obj, long id) {
        SetterId setterId = setterIdMap.get(key);
        if (Objects.isNull(setterId)) {
            LOG.debug("Didn't find setter id for object.");
        }
        return nonNull(setterId) ? setterId.setId(obj, id) : null;
    }

    public Object read(String key) {
        return store.get(key);
    }

    public Object update(String key, Object value) {
        Object previousVersion = store.get(key);
        if (nonNull(previousVersion)) {
            store.put(key, value);
        }
        return previousVersion;
    }

    public boolean delete(String key) {
        long id = getPartId(key);
        if (id > -1 && indexes.contains(id)) {
            store.remove(key);
            indexes.remove(id);
            return true;
        } else {
            LOG.debug("Didn't find setter id for object.");
            return false;
        }
    }

    @Override
    public List<Object> readAll(String partOfId) {
        ArrayList<String> keys = getKeysByPartOfId(partOfId);
        return getObjectsByKeys(keys);
    }

    private List<Object> getObjectsByKeys(ArrayList<String> keys) {
        ArrayList<Object> results = new ArrayList<>();
        keys.stream().forEach(key -> {
            Object temp = store.get(key);
            if (nonNull(temp)) {
                results.add(temp);
            }
        });
        return results;
    }

    private ArrayList<String> getKeysByPartOfId(String partOfId) {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Object> objects = Lists.newArrayList(store.keySet().toArray());
        objects.stream()
                .filter(val -> ((String) val).startsWith(partOfId))
                .forEach(val -> keys.add((String) val));
        return keys;
    }

    private long getPartId(String key) {
        String[] keyParts = key.split(":");
        return keyParts.length == 2 ? stringToLong(keyParts[1]) : -1;
    }

    private long stringToLong(String keyPart) {
        try {
            return Long.parseLong(keyPart);
        } catch (NumberFormatException e) {
            LOG.debug("Second part of key not a long");
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
        if (x > -1 && !indexes.contains(x)) {
            return x;
        } else if (!indexes.contains(x)) {
            return (x * -1);
        }
        return -1;
    }
}
