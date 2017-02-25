/**
 *
 */
package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class MWConfigContent {
    @Id
    @GeneratedValue
    private long id;

    @Lob
    private String content;

    private boolean contentUpdated = false;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isContentUpdated() {
        return contentUpdated;
    }

    public void setContentUpdated(boolean contentUpdated) {
        this.contentUpdated = contentUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
