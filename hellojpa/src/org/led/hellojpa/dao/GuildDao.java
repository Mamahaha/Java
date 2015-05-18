package org.led.hellojpa.dao;

import org.led.hellojpa.entity.GuildEntity;


public interface GuildDao {
	GuildEntity createGuildEntity(GuildEntity entity);
	Long getIdByName(String name);
}
