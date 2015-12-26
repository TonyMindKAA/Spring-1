package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.impl.mysql.UserAccountDAOMySql;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.model.impl.UserAccountBean;
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
public class UserAccountServiceMySqlTest {

    @InjectMocks
    private UserAccountServiceMySql userAccountServiceMySql;

    @Mock
    private UserAccountDAOMySql userAccountDAOMySql;

    @Test
    public void shouldReturnEmptyListOfEvents_whenInvokeSelectAll() {
        List<UserAccount> userAccounts = new ArrayList<>();
        when(userAccountDAOMySql.selectAll()).thenReturn(userAccounts);

        assertThat(userAccountServiceMySql.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shouldInsertTicket_whenInvokeInsert() {
        UserAccount userAccount = new UserAccountBean() {{
            setUserId(123413L);
            setPrepaidMoney(234222L);
        }};

        when(userAccountDAOMySql.insert(userAccount)).thenReturn(userAccount);

        assertTrue(userAccountServiceMySql.insert(userAccount).equals(userAccount));
    }

    @Test
    public void shouldUpdateTicket_whenInvokeUpdate() {
        UserAccount userAccount = new UserAccountBean();
        userAccount.setUserId(123413L);
        userAccount.setPrepaidMoney(234222L);
        userAccount.setId(1345135L);

        when(userAccountDAOMySql.update(userAccount)).thenReturn(userAccount);

        assertTrue(userAccountServiceMySql.update(userAccount).equals(userAccount));
    }


    @Test
    public void shouldReturnTicketById_whenInvokeSelectById() {
        UserAccount userAccount = new UserAccountBean() {{
            setId(12343L);
        }};

        when(userAccountDAOMySql.selectById(userAccount.getId())).thenReturn(userAccount);

        assertEquals(userAccountServiceMySql.selectById(userAccount.getId()).getId(), userAccount.getId());
    }

    @Test
    public void shoutDeleteElementById_whenElementExist() {
        long id = 222;

        when(userAccountServiceMySql.deleteById(id)).thenReturn(true);

        assertThat(userAccountServiceMySql.deleteById(id)).isTrue();
    }

}
