package org.led.tools.BmcDbOperator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class PMCH {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
	
	@Index    
	private String pipeName; // national unique (parse and get it from PMCH's user label)
	
	@Index
	private long gbr;
	
	@Index
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey
	@JoinColumn(name = "MBSFN_ID")
	private MBSFN mbsfn;	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGbr() {
		return gbr;
	}

	public void setGbr(long gbr) {
		this.gbr = gbr;
	}

	public MBSFN getMbsfn() {
		return mbsfn;
	}

	public void setMbsfn(MBSFN mbsfn) {
		this.mbsfn = mbsfn;
	}	

	public String getPipeName() {
		return pipeName;
	}

	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}
}
