package com.epam.cdp.anton.krynytskyi.domain.store;

import com.epam.cdp.anton.krynytskyi.api.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;
import com.epam.cdp.anton.krynytskyi.domain.model.Const;
import com.epam.cdp.anton.krynytskyi.domain.setter.id.SetterIdEventBean;
import com.epam.cdp.anton.krynytskyi.domain.setter.id.SetterIdTicketBean;
import com.epam.cdp.anton.krynytskyi.domain.setter.id.SetterIdUserBean;
import com.google.common.collect.Lists;

import java.util.*;

import static com.epam.cdp.anton.krynytskyi.domain.model.Const.*;
import static com.epam.cdp.anton.krynytskyi.domain.model.Const.TICKET_BEAN;
import static com.epam.cdp.anton.krynytskyi.domain.model.Const.USER_BEAN;

public class BookingStoreImpl implements BookingStore {

    private Map<String, Object> store = new HashMap<>();
    private Set<Long> indexSet = new HashSet<>();
    private Map<String, SetterId> casterMap = new HashMap<String, SetterId>() {
        {
            put(EVENT_BEAN, new SetterIdEventBean());
            put(TICKET_BEAN, new SetterIdTicketBean());
            put(USER_BEAN, new SetterIdUserBean());
        }
    };

    public List<Object> readAll() {
        return new ArrayList<>(Arrays.asList((store.values().toArray())));
    }

    public Object create(String key, Object value) {
        long newId = generateId();
        Object objWithId = setIdToObject(key, value, newId);
        if (objWithId != null) {
            indexSet.add(newId);
            store.put(key + ":" + newId, objWithId);
        }
        return store.get(key + ":" + newId);
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
    public List<Object> readAll(String partOfId) {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Object> results = new ArrayList<>();

        ArrayList<Object> objects = Lists.newArrayList(store.keySet().toArray());
        objects.stream()
                .filter(val-> ((String)val).startsWith(partOfId))
                .forEach(val -> keys.add((String) val));

        keys.stream().forEach(key ->{
            Object temp = store.get(key);
            if(Objects.nonNull(temp)){
                results.add(temp);
            }
        });
        return results;
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
