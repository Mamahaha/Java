package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AlarmText {

    private static final int TEXT_LENGTH = 16384;
    @Id
    @GeneratedValue
    private long id;

    @Column(length = TEXT_LENGTH)
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
