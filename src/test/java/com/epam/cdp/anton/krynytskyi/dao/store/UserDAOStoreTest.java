package com.epam.cdp.anton.krynytskyi.dao.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.epam.cdp.anton.krynytskyi.dao.store.UserDAOStore;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import com.epam.cdp.anton.krynytskyi.store.impl.BookingStoreImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOStoreTest {

    @InjectMocks
    private UserDAOStore userDAOStore;

    @Test
    public void shoutReturnEmptyListOfEvents_whenInvokeSelectAll() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        assertThat(userDAOStore.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shoutReturnListOfEventsWithThreeElements_whenInvokeSelectAll() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean());
        userDAOStore.insert(new UserBean());
        userDAOStore.insert(new UserBean());

        assertEquals(userDAOStore.selectAll().size(), 3);

    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        User ticket = userDAOStore.insert(new UserBean());

        assertEquals(userDAOStore.selectById(ticket.getId()).getId(), ticket.getId());

    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean());

        int wrongId = 222222;
        assertThat(userDAOStore.selectById(wrongId)).isNull();

    }

    @Test
    public void shoutInsertOneElement_whenInvokeInsertMethod() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean());

        assertEquals(userDAOStore.selectAll().size(), 1);

    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        User updateObj = new UserBean();
        User user = userDAOStore.insert(new UserBean());

        String name = "Bob";
        user.setName(name);
        updateObj.setId(user.getId());
        String name2 = "Bob1";
        updateObj.setName(name2);

        User previewValue = userDAOStore.update(updateObj);
        assertEquals(previewValue.getName(), name);

        User newUpdatedEvent = userDAOStore.selectById(updateObj.getId());
        assertEquals(newUpdatedEvent.getName(), name2);

    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistEvent() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        User updateObj = new UserBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(userDAOStore.update(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        User newObject = userDAOStore.insert(new UserBean());

        assertThat(userDAOStore.deleteById(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistEvent() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        int notExistElement = 999999999;

        assertThat(userDAOStore.deleteById(notExistElement)).isFalse();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteMethod() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        User newObject = userDAOStore.insert(new UserBean());

        assertThat(userDAOStore.delete(newObject)).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteNotExistEvent() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        int notExistElement = 999999999;
        User newObject = new UserBean() {{
            setId(notExistElement);
        }};

        assertThat(userDAOStore.delete(newObject)).isFalse();
    }

    @Test
    public void shouldReturnUserByEmail_whenEmailExist() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean() {{
            setEmail("bob1@email.com");
        }});
        userDAOStore.insert(new UserBean() {{
            setEmail("bob2@email.com");
        }});
        userDAOStore.insert(new UserBean() {{
            setEmail("bob3@email.com");
        }});
        userDAOStore.insert(new UserBean() {{
            setEmail("bob4@email.com");
        }});

        User user = userDAOStore.selectByEmail("bob4@email.com");
        assertTrue(user.getEmail().equals("bob4@email.com"));
    }

    @Test
    public void shouldReturnNull_whenEmailUserWithEmailNotExist() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean() {{
            setEmail("bob1@email.com");
        }});
        userDAOStore.insert(new UserBean() {{
            setEmail("bob2@email.com");
        }});
        userDAOStore.insert(new UserBean() {{
            setEmail("bob3@email.com");
        }});
        userDAOStore.insert(new UserBean() {{
            setEmail("bob4@email.com");
        }});

        assertThat(userDAOStore.selectByEmail("bob777@email.com")).isNull();
    }

    @Test
    public void shouldReturnThreeUsersByName_whenPageSizeThreePageFirs() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});

        assertEquals(userDAOStore.selectByName("Bob_1", 3, 1).size(), 3);
    }

    @Test
    public void shouldReturnZeroUsersByName_whenPageSizeThreePageSecond() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});

        assertEquals(userDAOStore.selectByName("Bob_1", 3, 2).size(), 0);
    }

    @Test
    public void shouldReturnOneUsersByName_whenPageSizeTwoPageSecond() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});

        assertEquals(userDAOStore.selectByName("Bob_1", 2, 2).size(), 1);
    }

    @Test
    public void shouldReturnZeroUsersByName_whenPageSizeTwoPageSecondAndNotExistUserWithSomeName() {
        userDAOStore.setBookingStore(new BookingStoreImpl());

        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_1");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});
        userDAOStore.insert(new UserBean() {{
            setName("Bob_4");}});

        assertEquals(userDAOStore.selectByName("Ivan", 2, 2).size(), 0);
    }
}
