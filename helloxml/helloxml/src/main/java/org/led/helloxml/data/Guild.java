package org.led.helloxml.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Guild {
	String name;
	int level;
	String flag;
	String token;
	String description;
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	
	@XmlElement
	public void setLevel(int level) {
		this.level = level;
	}
	public String getFlag() {
		return flag;
	}
	
	@XmlElement
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getToken() {
		return token;
	}
	
	@XmlElement
	public void setToken(String token) {
		this.token = token;
	}
	public String getDescription() {
		return description;
	}
	
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		String info = "name: " + name + "\nlevel: " + level
						+ "\nflag: " + flag + "\ntoken: " + token
						+ "\ndescription" + description + "\n";
		
		return info;
	}
	
}
