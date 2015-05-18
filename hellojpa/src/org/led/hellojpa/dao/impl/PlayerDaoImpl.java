package org.led.hellojpa.dao.impl;

import javax.persistence.EntityManager;

import org.led.hellojpa.dao.PlayerDao;
import org.led.hellojpa.entity.PlayerEntity;
import org.led.hellojpa.utils.PersistenceContext;

public class PlayerDaoImpl implements PlayerDao {

	private EntityManager entityManager;
	
	public PlayerDaoImpl(PersistenceContext context) {
		super();
		this.entityManager = context.getEntityManager();
	}
	
	public PlayerEntity createPlayerEntity(PlayerEntity entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

}
