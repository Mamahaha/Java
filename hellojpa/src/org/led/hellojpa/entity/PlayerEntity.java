package org.led.hellojpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.ForeignKey;
import org.apache.openjpa.persistence.jdbc.Index;


/**
 * The persistent class for the player database table.
 * 
 */
@Entity
@Table(name = "player")
public class PlayerEntity implements Serializable {

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
	
	@Column(name = "sex", nullable = false)
	private String sex;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@Column(name = "level", nullable = false)
	private int level;
	
	@Column(name = "score", nullable = false)
	private long score;
	
	@Index
	@ManyToOne
	@ForeignKey
	@JoinColumn(name = "guildID")
	private GuildEntity guild;
	
	public GuildEntity getGuild() {
		return guild;
	}

	public void setGuild(GuildEntity guild) {
		this.guild = guild;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String toString() {
		String info = "Id\t\tName\t\tLevel\t\tScore\t\tGuild\n";
		info += id + "\t\t" + name + "\t\t" + level + "\t\t" + score + "\t\t" + guild.getName() + "\n";

		return info;
	}

}

