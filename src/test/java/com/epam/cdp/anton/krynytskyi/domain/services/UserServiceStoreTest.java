package com.epam.cdp.anton.krynytskyi.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.epam.cdp.anton.krynytskyi.api.model.User;
import com.epam.cdp.anton.krynytskyi.domain.dao.store.UserDAOStore;
import com.epam.cdp.anton.krynytskyi.domain.model.UserBean;
import com.epam.cdp.anton.krynytskyi.domain.store.BookingStoreImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceStoreTest {

    @InjectMocks
    private UserDAOStore userDAOStore;
    @InjectMocks
    private UserServiceStore userServiceStore;

    @Before
    public void initialize() {
        userDAOStore.setBookingStore(new BookingStoreImpl());
        userServiceStore.setUserDAO(userDAOStore);
    }

    @Test
    public void shoutReturnEmptyListOfEvents_whenInvokeSelectAll() {

        assertThat(userDAOStore.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shoutReturnListOfEventsWithThreeElements_whenInvokeSelectAll() {

        userServiceStore.insert(new UserBean());
        userServiceStore.insert(new UserBean());
        userServiceStore.insert(new UserBean());

        assertEquals(userServiceStore.selectAll().size(), 3);

    }

    @Test
    public void shoutReturnUserById_whenInvokeSelectById() {

        User ticket = userServiceStore.insert(new UserBean());

        assertEquals(userServiceStore.selectById(ticket.getId()).getId(), ticket.getId());

    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {

        userServiceStore.insert(new UserBean());

        int wrongId = 222222;
        assertThat(userServiceStore.selectById(wrongId)).isNull();

    }

    @Test
    public void shoutInsertOneElement_whenInvokeInsertMethod() {

        userServiceStore.insert(new UserBean());

        assertEquals(userServiceStore.selectAll().size(), 1);

    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {

        User updateObj = new UserBean();
        User user = userServiceStore.insert(new UserBean());

        String name = "Bob";
        user.setName(name);
        updateObj.setId(user.getId());
        String name2 = "Bob1";
        updateObj.setName(name2);

        User previewValue = userServiceStore.update(updateObj);
        assertEquals(previewValue.getName(), name);

        User newUpdatedEvent = userServiceStore.selectById(updateObj.getId());
        assertEquals(newUpdatedEvent.getName(), name2);

    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistUser() {

        User updateObj = new UserBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(userServiceStore.update(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {

        User newObject = userServiceStore.insert(new UserBean());

        assertThat(userServiceStore.deleteById(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistUser() {

        int notExistElement = 999999999;

        assertThat(userServiceStore.deleteById(notExistElement)).isFalse();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteMethod() {

        User newObject = userServiceStore.insert(new UserBean());

        assertThat(userServiceStore.delete(newObject)).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteNotExistEvent() {

        int notExistElement = 999999999;
        User newObject = new UserBean() {{
            setId(notExistElement);
        }};

        assertThat(userServiceStore.delete(newObject)).isFalse();
    }

    @Test
    public void shouldReturnUserByEmail_whenEmailExist() {

        userServiceStore.insert(new UserBean() {{
            setEmail("bob1@email.com");
        }});
        userServiceStore.insert(new UserBean() {{
            setEmail("bob2@email.com");
        }});
        userServiceStore.insert(new UserBean() {{
            setEmail("bob3@email.com");
        }});
        userServiceStore.insert(new UserBean() {{
            setEmail("bob4@email.com");
        }});

        User user = userServiceStore.selectByEmail("bob4@email.com");
        assertTrue(user.getEmail().equals("bob4@email.com"));
    }

    @Test
    public void shouldReturnNull_whenEmailUserWithEmailNotExist() {

        userServiceStore.insert(new UserBean() {{
            setEmail("bob1@email.com");
        }});
        userServiceStore.insert(new UserBean() {{
            setEmail("bob2@email.com");
        }});
        userServiceStore.insert(new UserBean() {{
            setEmail("bob3@email.com");
        }});
        userServiceStore.insert(new UserBean() {{
            setEmail("bob4@email.com");
        }});

        assertThat(userServiceStore.selectByEmail("bob777@email.com")).isNull();
    }

    @Test
    public void shouldReturnThreeUsersByName_whenPageSizeThreePageFirs() {

        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(userServiceStore.selectByName("Bob_1", 3, 1).size(), 3);
    }

    @Test
    public void shouldReturnZeroUsersByName_whenPageSizeThreePageSecond() {

        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(userServiceStore.selectByName("Bob_1", 3, 2).size(), 0);
    }

    @Test
    public void shouldReturnOneUsersByName_whenPageSizeTwoPageSecond() {

        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(userServiceStore.selectByName("Bob_1", 2, 2).size(), 1);
    }

    @Test
    public void shouldReturnZeroUsersByName_whenPageSizeTwoPageSecondAndNotExistUserWithSomeName() {

        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_1");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});
        userServiceStore.insert(new UserBean() {{
            setName("Bob_4");
        }});

        assertEquals(userServiceStore.selectByName("Ivan", 2, 2).size(), 0);
    }
}
