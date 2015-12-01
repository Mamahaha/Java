/**
 * 
 */
package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

/**
 * @author eweitan
 * 
 */
@Entity
public class UserService {

    @Id
    @GeneratedValue
    private long id;

    @Index
    private String name;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "BearerID")
    private Bearer bearer;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "FECID")
    private FECTemplate fecTemplate;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "RRID")
    private RRTemplate rrTemplate;

    @Index
    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "FRID")
    private FRTemplate frTemplate;

    private String description;

    private int maxBitrate;

    private String mode;

    @ManyToOne
    @ForeignKey
    @JoinColumn(name = "QoeMetricsID")
    private QoeMetrics qoeMetrics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxBitrate() {
        return maxBitrate;
    }

    public void setMaxBitrate(int maxBitrate) {
        this.maxBitrate = maxBitrate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public QoeMetrics getQoeMetrics() {
        return qoeMetrics;
    }

    public void setQoeMetrics(QoeMetrics qoeMetrics) {
        this.qoeMetrics = qoeMetrics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bearer getBearer() {
        return bearer;
    }

    public void setBearer(Bearer bearer) {
        this.bearer = bearer;
    }

    public FECTemplate getFecTemplate() {
        return fecTemplate;
    }

    public void setFecTemplate(FECTemplate fecTemplate) {
        this.fecTemplate = fecTemplate;
    }

    public RRTemplate getRrTemplate() {
        return rrTemplate;
    }

    public void setRrTemplate(RRTemplate rrTemplate) {
        this.rrTemplate = rrTemplate;
    }

    public FRTemplate getFrTemplate() {
        return frTemplate;
    }

    public void setFrTemplate(FRTemplate frTemplate) {
        this.frTemplate = frTemplate;
    }
}
