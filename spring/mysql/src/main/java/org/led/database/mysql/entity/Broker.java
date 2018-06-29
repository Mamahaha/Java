package org.led.database.mysql.entity;

import org.led.database.mysql.common.entity.AbstractEntity;
import org.led.database.mysql.def.BrokerTypeEnum;
import org.led.database.mysql.def.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Broker extends AbstractEntity {

    private Long id;
    private String name;
    private CategoryEnum category;
    private String ip;
    private int port;
    private BrokerTypeEnum type;
    private boolean enable;

    @Override
    public String toString() {
        return "Broker id: " + id
            + "; name: " + name
            + "; categoryEnum: " + category
            + "; ip: " + ip
            + "; port: " + port
            + "; type: " + type
            + "; enable: " + enable;
    }
}
