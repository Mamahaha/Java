package org.led.bigdata.reporting.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "t_reporting")
@NamedQuery(name = "reporting.findAll", query = "SELECT b FROM ReportingEntity b")
public class ReportingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "count", nullable = false)
	private int count;
	
	@Column(name = "arriveTime", nullable = true)
	private int arriveTime;
	
	@Column(name = "requestURI", nullable = true)
	private String requestURI;
	
	@Column(name = "sourceAddress", nullable = true)
	private String sourceAddress;
	
	@Column(name = "requestXML", nullable = true)
	private String requestXML;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	
	
}
