package org.led.hellojpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the guild database table.
 * 
 */
@Entity
@Table(name = "guild")
@NamedQuery(name = "guild.findAll", query = "SELECT b FROM GuildEntity b")
public class GuildEntity implements Serializable {

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
	
	@Column(name = "level", nullable = false)
	private int level;
	
	@Column(name = "count", nullable = false)
	private int count;
	
	@Column(name = "flag", nullable = false)
	private String flag;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		String guildInfo = "id: " + id + "\nname: " + name + "\nlevel: " + level
						+ "\ncount: " + count + "\ntoken: " + token + "\n";
		return guildInfo;
	}
}
