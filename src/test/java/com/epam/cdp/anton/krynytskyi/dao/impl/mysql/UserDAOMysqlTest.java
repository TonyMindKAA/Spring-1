package com.epam.cdp.anton.krynytskyi.dao.impl.mysql;

import com.epam.cdp.anton.krynytskyi.mapers.UserRowMapper;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.impl.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOMysqlTest {

    public static final String SELECT_ALL_USER = "select * from user";
    public static final String SELECT_USER_BY_ID = "select * from user where id = ?";
    public static final String INSERT_USER = "insert into user (name, email) values (?, ?)";
    public static final String UPDATE_USER = "update user set name = ?, email = ? where id = ?";
    public static final String DELETE_USER = "delete from user where id = ?";
    public static final String SELECT_USER_BY_EMAIL = "select * from user where email = ?";
    public static final String SELECT_USER_BY_NAME = "select * from user where name = ? limit ?, ?";

    @InjectMocks
    private UserDAOMySql userDAO;

    @Mock
    private JdbcTemplate jdbcTemplateObject;

    @Mock
    private UserRowMapper userRowMapper;

    @Test
    public void shouldReturnEmptyListOfUsers_whenInvokeSelectAll() {
        List<User> users = new ArrayList<>();
        when(jdbcTemplateObject.query(SELECT_ALL_USER,
                new UserRowMapper())).thenReturn(users);

        assertThat(userDAO.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertUser_whenInvokeInsert() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
        }};

        when(jdbcTemplateObject.update(INSERT_USER,
                user.getName(),
                user.getEmail())).thenReturn(1);

        assertTrue(userDAO.insert(user).equals(user));
    }

    @Test
    public void shouldUpdateUser_whenInvokeUpdate() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
            setId(2452435);
        }};

        when(jdbcTemplateObject.update(UPDATE_USER,
                user.getName(),
                user.getEmail(),
                user.getId())).thenReturn(1);

        assertTrue(userDAO.insert(user).equals(user));
    }


    @Test
    public void shouldReturnUserById_whenInvokeSelectById() {
        UserBean user = new UserBean() {{
            setName("bob");
            setEmail("bob@email.ru");
            setId(2452435);
        }};

        when(jdbcTemplateObject.queryForObject(SELECT_USER_BY_ID, new Object[]{user.getId()}, userRowMapper)).thenReturn(user);

        assertEquals(userDAO.selectById(user.getId()).getId(), user.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(jdbcTemplateObject.update(DELETE_USER, id)).thenReturn(1);

        assertThat(userDAO.deleteById(id)).isTrue();
    }

    @Test
    public void shoutDeleteElementByUserObj_whenElementExist() {
        UserBean userBean = new UserBean() {{
            setId(222L);
        }};

        when(jdbcTemplateObject.update(DELETE_USER, userBean.getId())).thenReturn(1);

        assertThat(userDAO.delete(userBean)).isTrue();
    }

    @Test
    public void shouldSelectUserByEmail_whenUserWithCurrentEmailExist() {
        UserBean userBean = new UserBean() {{
            setEmail("sample@email.com");
        }};

        when(jdbcTemplateObject.queryForObject(SELECT_USER_BY_EMAIL,
                new Object[]{userBean.getEmail()},
                userRowMapper)).thenReturn(userBean);

        assertThat(userDAO.selectByEmail(userBean.getEmail()).equals(userBean)).isTrue();
    }

    @Test
    public void shouldReturnListWithOneUser_when() {
        UserBean userBean = new UserBean() {{
            setName("sampleName");
        }};
        List<User> users = new ArrayList<>();
        users.add(userBean);
        int paginationSize = 0;
        int pageSize = 3;
        int pageNum = 1;

        when(jdbcTemplateObject.query(SELECT_USER_BY_NAME,
                new Object[]{userBean.getName(), paginationSize, pageSize},
                userRowMapper)).thenReturn(users);

        assertEquals(userDAO.selectByName(userBean.getName(), pageSize, pageNum).size(), users.size());
    }
}
