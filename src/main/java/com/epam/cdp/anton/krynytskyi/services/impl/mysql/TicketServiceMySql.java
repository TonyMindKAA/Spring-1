package com.epam.cdp.anton.krynytskyi.services.impl.mysql;

import com.epam.cdp.anton.krynytskyi.dao.EventDAO;
import com.epam.cdp.anton.krynytskyi.dao.TicketDAO;
import com.epam.cdp.anton.krynytskyi.dao.UserAccountDAO;
import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.model.Ticket;
import com.epam.cdp.anton.krynytskyi.model.User;
import com.epam.cdp.anton.krynytskyi.model.UserAccount;
import com.epam.cdp.anton.krynytskyi.services.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class TicketServiceMySql implements TicketService {

    private static Logger LOG = Logger.getLogger("TicketServiceMySql");

    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;

    @Autowired
    @Qualifier("ticketDAOMySql")
    private TicketDAO ticketDAO;

    @Autowired
    @Qualifier("userAccountDAOMySql")
    private UserAccountDAO userAccountDAO;

    @Autowired
    @Qualifier("eventDAOMySql")
    private EventDAO eventDAO;



    public TicketServiceMySql() {
    }

    public List<Ticket> selectAll() {
        return ticketDAO.selectAll();
    }

    public Ticket selectById(long id) {
        return ticketDAO.selectById(id);
    }

    public Ticket insert(Ticket ticket) {
        return ticketDAO.insert(ticket);
    }

    public Ticket update(Ticket ticket) {
        return ticketDAO.update(ticket);
    }

    public boolean delete(Ticket ticket) {
        return ticketDAO.delete(ticket);
    }

    public boolean deleteById(long id) {
        return ticketDAO.deleteById(id);
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            UserAccount userAccount = userAccountDAO.selectByUserId(userId);
            Event event = eventDAO.selectById(eventId);
            long accountBalance = userAccount.getPrepaidMoney() - event.getTicketPrice();
            if (accountBalance > 0) {
                userAccount.setPrepaidMoney(accountBalance);
                userAccountDAO.update(userAccount);
                return ticketDAO.bookTicket(userId, eventId, place, category);
            } else {
                LOG.error("Cann't create event, because not enough money.");
                transactionManager.rollback(status);
                return null;
            }
        } catch (DataAccessException e) {
            LOG.error("Cann't create event.");
            transactionManager.rollback(status);
            return null;
        }
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(event, pageSize, pageNum);
    }
}
