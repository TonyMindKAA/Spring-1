package com.epam.cdp.anton.krynytskyi.dao.impl.mysql;

import com.epam.cdp.anton.krynytskyi.mapers.UserAccountRowMapper;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.model.impl.TicketBean;
import com.epam.cdp.anton.krynytskyi.model.impl.UserAccountBean;
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
public class UserAccountDAOMySqlTest {

    @InjectMocks
    private UserAccountDAOMySql userAccountDAOMySql;

    @Mock
    private JdbcTemplate jdbcTemplateObject;

    @Mock
    private UserAccountRowMapper userAccountRowMapper;

    public static final String SELECT_ALL_USER_ACCOUNT = "select * from user_account";
    public static final String SELECT_USER_ACCOUNT_BY_ID = "select * from user_account where id = ?";
    public static final String SELECT_USER_ACCOUNT_BY_USER_ID = "select * from user_account where userId = ?";
    public static final String INSERT_USER_ACCOUNT = "insert into user_account (userId, prepaid_money) values (?, ?)";
    public static final String UPDATE_USER_ACCOUNT = "update user_account set userId = ?, prepaid_money = ? where id = ?";
    public static final String DELETE_USER_ACCOUNT = "delete from user_account where id = ?";


    @Test
    public void shouldReturnEmptyListOfEvents_whenInvokeSelectAll() {
        List<UserAccount> events = new ArrayList<>();
        when(jdbcTemplateObject.query(SELECT_ALL_USER_ACCOUNT, userAccountRowMapper)).thenReturn(events);

        assertThat(userAccountDAOMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertTicket_whenInvokeInsert() {
        UserAccount userAccount = new UserAccountBean() {{
            setUserId(123413L);
            setPrepaidMoney(234222L);
        }};

        when(jdbcTemplateObject.update(INSERT_USER_ACCOUNT,
                userAccount.getUserId(),
                userAccount.getPrepaidMoney())).thenReturn(1);

        assertTrue(userAccountDAOMySql.insert(userAccount).equals(userAccount));
    }

    @Test
    public void shouldUpdateTicket_whenInvokeUpdate() {
        UserAccount userAccount = new UserAccountBean() {{
            setUserId(123413L);
            setPrepaidMoney(234222L);
            setId(1345135L);
        }};

        when(jdbcTemplateObject.update(INSERT_USER_ACCOUNT,
                userAccount.getUserId(),
                userAccount.getPrepaidMoney())).thenReturn(1);

        assertTrue(userAccountDAOMySql.update(userAccount).equals(userAccount));
    }


    @Test
    public void shouldReturnTicketById_whenInvokeSelectById() {
        UserAccount userAccount = new UserAccountBean() {{
            setId(12343L);
        }};

        when(jdbcTemplateObject.queryForObject(SELECT_USER_ACCOUNT_BY_ID,
                new Object[]{userAccount.getId()},
                userAccountRowMapper)).thenReturn(userAccount);

        assertEquals(userAccountDAOMySql.selectById(userAccount.getId()).getId(), userAccount.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(jdbcTemplateObject.update(DELETE_USER_ACCOUNT, id)).thenReturn(1);

        assertThat(userAccountDAOMySql.deleteById(id)).isTrue();
    }

}
