package com.epam.cdp.anton.krynytskyi.dao.store;

import static com.epam.cdp.anton.krynytskyi.model.Const.USER_BEAN;
import static java.util.Objects.nonNull;

import com.epam.cdp.anton.krynytskyi.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.store.BookingStore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAOStore implements UserDAO {

    private BookingStore bookingStore;

    public void setBookingStore(BookingStore bookingStore) {
        this.bookingStore = bookingStore;
    }

    public List<User> selectAll() {
        List<Object> objectList = bookingStore.readAll(USER_BEAN);
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
        Object readUser = bookingStore.read(USER_BEAN + ":" + id);
        return nonNull(readUser) ? (User) readUser : null;
    }

    public User insert(User user) {
        Object insertedUser = bookingStore.create(USER_BEAN, user);
        return nonNull(insertedUser) ? (User) insertedUser : null;
    }

    public User update(User user) {
        Object updatedUser = bookingStore.update(USER_BEAN + ":" + user.getId(), user);
        return nonNull(updatedUser) ? (User) updatedUser : null;
    }

    public boolean delete(User user) {
        return nonNull(user) ? deleteById(user.getId()) : false;
    }

    public boolean deleteById(long id) {
        return bookingStore.delete(USER_BEAN + ":" + id);
    }

    @Override
    public User selectByEmail(String email) {
        List<User> events = selectAll();
        List<User> userList = events.stream()
                .filter(e -> e.getEmail().equals(email))
                .collect(Collectors.toList());
        return userList.size() > 0 ? userList.get(0) : null;
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
