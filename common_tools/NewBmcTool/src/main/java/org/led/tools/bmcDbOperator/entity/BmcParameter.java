package org.led.tools.bmcDbOperator.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.openjpa.persistence.jdbc.Index;

@Entity
public class BmcParameter {
	
	@Id
	@Index
	private String name;
	
	private String value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
