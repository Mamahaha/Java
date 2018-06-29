package org.led.database.mysql.common.entity;

import org.led.database.mysql.common.spec.Identifiable;
import java.io.Serializable;


public abstract class AbstractEntity implements Identifiable<Long>, Serializable {

    @Override
    public Long getId() {
        return 0L;
    }
}
