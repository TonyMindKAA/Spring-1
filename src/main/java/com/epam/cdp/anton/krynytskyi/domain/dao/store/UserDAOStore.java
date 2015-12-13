package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import com.epam.cdp.anton.krynytskyi.api.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;

import java.util.List;

public class UserDAOStore implements UserDAO {

    public static final String PART_OF_ID = "ticket:";
    private BookingStore bookingStore;

    public List<User> selectAll() {
        // TODO: think about it: return bookingStore.getAll();
        return null;
    }

    public User selectById(long id) {
        return null;
    }

    public User insert(User user) {
        return null;
    }

    public User update(User user) {
        return null;
    }

    public void delete(User user) {

    }

    public void deleteById(long id) {

    }
}
