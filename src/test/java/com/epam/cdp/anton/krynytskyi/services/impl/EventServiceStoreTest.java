package com.epam.cdp.anton.krynytskyi.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.epam.cdp.anton.krynytskyi.model.Event;
import com.epam.cdp.anton.krynytskyi.dao.store.EventDAOStore;
import com.epam.cdp.anton.krynytskyi.model.impl.EventBean;
import com.epam.cdp.anton.krynytskyi.services.impl.EventServiceStore;
import com.epam.cdp.anton.krynytskyi.store.impl.BookingStoreImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;


@RunWith(MockitoJUnitRunner.class)
public class EventServiceStoreTest {

    @InjectMocks
    private EventDAOStore eventDAOStore;

    @InjectMocks
    private EventServiceStore eventServiceStore;

    @Before
    public void initialize() {
        eventDAOStore.setBookingStore(new BookingStoreImpl());
        eventServiceStore.setEventDAO(eventDAOStore);
    }

    @Test
    public void shoutReturnEmptyListOfEvents_whenInvokeSelectAll() {

        assertThat(eventServiceStore.selectAll().isEmpty()).isTrue();
    }

    @Test
    public void shoutReturnListOfEventsWithThreeElements_whenInvokeSelectAll() {

        eventServiceStore.insert(new EventBean());
        eventServiceStore.insert(new EventBean());
        eventServiceStore.insert(new EventBean());

        assertEquals(eventServiceStore.selectAll().size(), 3);

    }

    @Test
    public void shoutReturnEventsById_whenInvokeSelectById() {

        Event newEvent = eventServiceStore.insert(new EventBean());

        assertEquals(eventServiceStore.selectById(newEvent.getId()).getId(), newEvent.getId());

    }

    @Test
    public void shoutInvokeSelectByIdReturnNull_whenPutWrongId() {

        Event newEvent = eventServiceStore.insert(new EventBean());

        int wrongId = 222222;
        assertThat(eventServiceStore.selectById(wrongId)).isNull();

    }

    @Test
    public void shoutInsertOneElement_whenInvokeInsertMethod() {

        Event newEvent = eventServiceStore.insert(new EventBean());

        assertEquals(eventServiceStore.selectAll().size(), 1);

    }

    @Test
    public void shoutUpdateOneElement_whenInvokeUpdateMethod() {

        Event updateObj = new EventBean();
        Event newEvent = eventServiceStore.insert(new EventBean());

        String title = "test-tittle1";
        newEvent.setTitle(title);
        updateObj.setId(newEvent.getId());
        String title2 = "test-tittle2";
        updateObj.setTitle(title2);

        Event previewValue = eventServiceStore.update(updateObj);
        assertThat(previewValue.getTitle().equals(title)).isTrue();

        Event newUpdatedEvent = eventServiceStore.selectById(updateObj.getId());
        assertThat(newUpdatedEvent.getTitle().equals(title2)).isTrue();
    }

    @Test
    public void shoutReturnNull_whenTryToUpdateNotExistEvent() {

        Event updateObj = new EventBean();
        int notExistElement = 999999999;
        updateObj.setId(notExistElement);
        assertThat(eventServiceStore.update(updateObj)).isNull();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteByIdMethod() {

        Event newObject = eventServiceStore.insert(new EventBean());

        assertThat(eventServiceStore.deleteById(newObject.getId())).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteByIdNotExistEvent() {

        int notExistElement = 999999999;

        assertThat(eventServiceStore.deleteById(notExistElement)).isFalse();
    }

    @Test
    public void shoutDeleteEvent_whenInvokDeleteMethod() {

        Event newObject = eventServiceStore.insert(new EventBean());

        assertThat(eventServiceStore.delete(newObject)).isTrue();
    }

    @Test
    public void shoutReturnFalse_whenTryDeleteNotExistEvent() {

        int notExistElement = 999999999;
        Event newObject = new EventBean() {{
            setId(notExistElement);
        }};

        assertThat(eventServiceStore.delete(newObject)).isFalse();
    }

    @Test
    public void shoutReturnThreeEventsWithSomeTittle_whenInvokeSelectByTittle() {

        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(eventServiceStore.selectByTitle("t2", 3, 1).size(), 3);
    }

    @Test
    public void shoutReturnZeroEventsWithSomeTittle_whenInvokeSelectByTittlePageSizeThreePageSecond() {

        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(eventServiceStore.selectByTitle("t2", 3, 2).size(), 0);
    }

    @Test
    public void shoutReturnOneEventsWithSomeTittle_whenInvokeSelectByTittlePageSizeTwoPageSecond() {

        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t1");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});
        eventServiceStore.insert(new EventBean() {{
            setTitle("t2");
        }});

        assertEquals(eventServiceStore.selectByTitle("t2", 2, 2).size(), 1);
    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeThreePageFirst() {

        long beforeDec_15_2015 = 1450055646943l;
        long dec_15_2015 = 1450188646943l;
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(eventServiceStore.selectForDay(new Date(beforeDec_15_2015), 3, 1).size(), 3);

    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeThreePageSecond() {

        long beforeDec_15_2015 = 1450055646943l;
        long dec_15_2015 = 1450188646943l;
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(eventServiceStore.selectForDay(new Date(beforeDec_15_2015), 3, 2).size(), 0);

    }

    @Test
    public void shoutReturnOneEventsForDay_whenInvokeSelectForDayPageSizeTwoPageSecond() {

        long beforeDec_15_2015 = 1450055646943l;
        long dec_15_2015 = 1450188646943l;
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(dec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});
        eventServiceStore.insert(new EventBean() {{
            setDate(new Date(beforeDec_15_2015));
        }});

        assertEquals(eventServiceStore.selectForDay(new Date(beforeDec_15_2015), 2, 2).size(), 1);
    }

}
