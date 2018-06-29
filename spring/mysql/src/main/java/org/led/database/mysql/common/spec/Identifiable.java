package org.led.database.mysql.common.spec;

import java.io.Serializable;

/**
 * Interface to mark objects that are identifiable by an ID of any type.
 *
 * Just replace org.springframework.hateoas.Identifiable
 */

public interface Identifiable<ID extends Serializable> {


    /**
     * Returns the id identifying the object.
     *
     * @return the identifier or {@literal null} if not available.
     */
    ID getId();

}