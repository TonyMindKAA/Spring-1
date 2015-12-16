package com.epam.cdp.anton.krynytskyi.object.casting;

import com.epam.cdp.anton.krynytskyi.model.AbstractBean;

public interface SetterId<T extends AbstractBean> {
    T setId(Object obj, long id);
}
