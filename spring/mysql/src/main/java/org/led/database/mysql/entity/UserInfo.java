package org.led.database.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private int id;
    private String user_name;
    private String password;
    private int broker_id;
    private String email;
    private boolean active;
    private String create_time;

    @Override
    public String toString() {
        return "UserInfo id: " + id
            + "; userName: " + user_name
            + "; password: " + password
            + "; brokerId: " + broker_id
            + "; email: " + email
            + "; active: " + active
            + "; createTime: " + create_time;
    }
}
