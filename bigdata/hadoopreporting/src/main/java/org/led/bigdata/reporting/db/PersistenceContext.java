package org.led.bigdata.reporting.db;

import javax.persistence.EntityManager;

public class PersistenceContext {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}