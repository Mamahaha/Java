package org.led.hellojpa.dao;

import org.led.hellojpa.entity.GuildEntity;

import org.led.hellojpa.entity.PlayerEntity;

public interface PlayerDao {
	PlayerEntity createPlayerEntity(PlayerEntity entity);
	GuildEntity getGuildByPlayerName (String name);
}
