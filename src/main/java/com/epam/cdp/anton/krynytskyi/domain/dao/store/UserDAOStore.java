package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import com.epam.cdp.anton.krynytskyi.api.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.api.store.BookingStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDAOStore implements UserDAO {

    public static final String PART_OF_ID = "ticket:";
    private BookingStore bookingStore;

    public void setBookingStore(BookingStore bookingStore) {
        this.bookingStore = bookingStore;
    }

    public List<User> selectAll() {
        List<Object> objectList = bookingStore.readAll(PART_OF_ID);
        List<User> eventList = new ArrayList<>();

        objectList.stream()
                .filter(elt -> elt != null)
                .forEach(elt -> eventList.add(castToUser(elt)));

        return eventList;
    }

    private User castToUser(Object elt) {
        return ((User) elt);
    }

    public User selectById(long id) {
        Object readUser = bookingStore.read(PART_OF_ID + id);
        return readUser != null ? (User) readUser : null;
    }

    public User insert(User user) {
        Object insertedUser = bookingStore.create(PART_OF_ID + user.getId(), user);
        return user != null ? (User) insertedUser : null;
    }

    public User update(User user) {
        Object updatedUser = bookingStore.update(PART_OF_ID + user.getId(), user);
        return user != null ? (User) updatedUser : null;
    }

    public boolean delete(User user) {
        return user != null ? deleteById(user.getId()) : false;
    }

    public boolean deleteById(long id) {
        return bookingStore.delete(PART_OF_ID + id);
    }

    @Override
    public User selectByEmail(String email) {
        List<User> events = selectAll();
        List<User> userList = events.stream()
                .filter(e -> e.getEmail().equals(email))
                .collect(Collectors.toList());
        return userList.size() >0? userList.get(0): null;
    }

    @Override
    public List<User> selectByName(String name, int pageSize, int pageNum) {
        List<User> events = selectAll();
        int paginationSize = pageSize * (pageNum - 1);
        return events.stream()
                .filter(e -> e.getName().equals(name))
                .skip(paginationSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
