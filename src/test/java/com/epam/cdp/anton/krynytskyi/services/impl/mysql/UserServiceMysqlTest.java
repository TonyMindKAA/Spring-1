package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.UserDAO;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMysqlTest {

    @InjectMocks
    private UserServiceMySql userServiceMySql;

    @Mock
    private UserDAO userDAO;

    @Test
    public void shouldReturnEmptyListOfUsers_whenInvokeSelectAll() {
        List<User> users = new ArrayList<>();

        when(userDAO.selectAll()).thenReturn(users);

        assertThat(userServiceMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertUser_whenInvokeInsert() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
        }};

        when(userDAO.insert(user)).thenReturn(user);

        assertTrue(userServiceMySql.insert(user).equals(user));
    }

    @Test
    public void shouldUpdateUser_whenInvokeUpdate() {
        UserBean user = new UserBean();
        user.setName("bob");
        user.setEmail("bob@email.ru");
        user.setId(2452435);

        when(userDAO.update(user)).thenReturn(user);

        assertTrue(userServiceMySql.update(user).equals(user));
    }


    @Test
    public void shouldReturnUserById_whenInvokeSelectById() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
            setId(2452435);
        }};

        when(userDAO.selectById(user.getId())).thenReturn(user);

        assertEquals(userServiceMySql.selectById(user.getId()).getId(), user.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(userDAO.deleteById(id)).thenReturn(true);

        assertThat(userServiceMySql.deleteById(id)).isTrue();
    }

    @Test
    public void shoutDeleteElementByUserObj_whenElementExist() {
        UserBean userBean = new UserBean() {{
            setId(222L);
        }};

        when(userDAO.delete(userBean)).thenReturn(true);

        assertThat(userServiceMySql.delete(userBean)).isTrue();
    }

    @Test
    public void shouldSelectUserByEmail_whenUserWithCurrentEmailExist() {
        UserBean userBean = new UserBean() {{
            setEmail("sample@email.com");
        }};

        when(userDAO.selectByEmail(userBean.getEmail())).thenReturn(userBean);

        assertThat(userServiceMySql.selectByEmail(userBean.getEmail()).equals(userBean)).isTrue();
    }

    @Test
    public void shouldReturnListWithOneUser_when() {
        UserBean userBean = new UserBean() {{
            setName("sampleName");
        }};
        List<User> users = new ArrayList<>();
        users.add(userBean);
        int pageSize = 3;
        int pageNum = 1;

        when(userDAO.selectByName(userBean.getName(), pageSize, pageNum)).thenReturn(users);

        assertEquals(userServiceMySql.selectByName(userBean.getName(), pageSize, pageNum).size(), users.size());
    }
}
