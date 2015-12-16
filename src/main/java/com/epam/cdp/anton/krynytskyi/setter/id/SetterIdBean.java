package com.epam.cdp.anton.krynytskyi.setter.id;

import com.epam.cdp.anton.krynytskyi.object.casting.SetterId;
import com.epam.cdp.anton.krynytskyi.model.AbstractBean;

public class SetterIdBean<T extends AbstractBean> implements SetterId<AbstractBean> {

    public T setId(Object obj, long id) {
        T element = (T) obj;
        element.setId(id);
        return element;
    }
}
