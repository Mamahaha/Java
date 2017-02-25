/**
 *
 */
package org.led.tools.bmcDbOperator.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "schemaName"))
public class MWConfigSchema {
    @Id
    @GeneratedValue
    private long id;
    
    private String schemaName;
    
    @Lob
    private String schemaContent;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    
    public String getSchemaContent() {
        return schemaContent;
    }

    public void setSchemaContent(String schemaContent) {
        this.schemaContent = schemaContent;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
