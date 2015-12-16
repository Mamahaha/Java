package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author exiaowu
 *
 */
@Entity
public class BmcRole {

    private static final int LENGTH = 1024;

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Column(length=LENGTH)
    private String description;

    public BmcRole() {
    }

    public BmcRole(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public BmcRole(String name, String[][] roleTable) {
        super();
        this.name = name;
        this.description = "";
        for (int i = 0; i < roleTable.length; i++) {
            String[] pair = roleTable[i];
            this.description += pair[0];
            this.description += ":" + pair[1] + ";";

        }
        // this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desr) {
        this.description = desr;
    }

}
