package com.epam.cdp.anton.krynytskyi.domain.dao.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.epam.cdp.anton.krynytskyi.api.model.Event;
import com.epam.cdp.anton.krynytskyi.domain.model.EventBean;
import com.epam.cdp.anton.krynytskyi.domain.store.BookingStoreImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;


@RunWith(MockitoJUnitRunner.class)
public class EventDAOStoreTest {

    @InjectMocks
    private EventDAOStore eventDAOStore;

    @Test
    public void shoutReturnEmptyListOfEvents_whenInvokeSelectAll() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        assertThat(eventDAOStore.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shoutReturnListOfEventsWithThreeElements_whenInvokeSelectAll() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        eventDAOStore.insert(new EventBean());
        eventDAOStore.insert(new EventBean());
        eventDAOStore.insert(new EventBean());

        assertEquals(eventDAOStore.selectAll().size(), 3);

    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event newEvent = eventDAOStore.insert(new EventBean());

        assertEquals(eventDAOStore.selectById(newEvent.getId()).getId(), newEvent.getId());

    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event newEvent = eventDAOStore.insert(new EventBean());

        int wrongId = 222222;
        assertThat(eventDAOStore.selectById(wrongId)).isNull();

    }

    @Test
    public void shoutInsertOneElement_whenInvokeInsertMethod() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event newEvent = eventDAOStore.insert(new EventBean());

        assertEquals(eventDAOStore.selectAll().size(), 1);

    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event updateObj = new EventBean();
        Event newEvent = eventDAOStore.insert(new EventBean());

        String title = "test-tittle1";
        newEvent.setTitle(title);
        updateObj.setId(newEvent.getId());
        String title2 = "test-tittle2";
        updateObj.setTitle(title2);

        Event previewValue = eventDAOStore.update(updateObj);
        assertThat(previewValue.getTitle().equals(title)).isTrue();

        Event newUpdatedEvent = eventDAOStore.selectById(updateObj.getId());
        assertThat(newUpdatedEvent.getTitle().equals(title2)).isTrue();
    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistEvent() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event updateObj = new EventBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(eventDAOStore.update(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event newObject = eventDAOStore.insert(new EventBean());

        assertThat(eventDAOStore.deleteById(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistEvent() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        int notExistElement = 999999999;

        assertThat(eventDAOStore.deleteById(notExistElement)).isFalse();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteMethod() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        Event newObject = eventDAOStore.insert(new EventBean());

        assertThat(eventDAOStore.delete(newObject)).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteNotExistEvent() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        int notExistElement = 999999999;
        Event newObject = new EventBean() {{
            setId(notExistElement);
        }};

        assertThat(eventDAOStore.delete(newObject)).isFalse();
    }

    @Test
    public void shoutReturnThreeEventsWithSomeTittle_whenInvokeSelectByTittle() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(eventDAOStore.selectByTitle("t2", 3, 1).size(), 3);
    }

    @Test
    public void shoutReturnZeroEventsWithSomeTittle_whenInvokeSelectByTittlePageSizeThreePageSecond() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(eventDAOStore.selectByTitle("t2", 3, 2).size(), 0);
    }

    @Test
    public void shoutReturnOneEventsWithSomeTittle_whenInvokeSelectByTittlePageSizeTwoPageSecond() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventDAOStore.insert(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(eventDAOStore.selectByTitle("t2", 2, 2).size(), 1);
    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeThreePageFirst() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        long beforeDec_15_2015 = 1450055646943l;
        long dec_15_2015 = 1450188646943l;
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(eventDAOStore.selectForDay(new Date(beforeDec_15_2015), 3, 1).size(), 3);

    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeThreePageSecond() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        long beforeDec_15_2015 = 1450055646943l;
        long dec_15_2015 = 1450188646943l;
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(eventDAOStore.selectForDay(new Date(beforeDec_15_2015), 3, 2).size(), 0);

    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeTwoPageSecond() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());

        long beforeDec_15_2015 = 1450055646943l;
        long dec_15_2015 = 1450188646943l;
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventDAOStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(eventDAOStore.selectForDay(new Date(beforeDec_15_2015), 2, 2).size(), 1);
    }

}
