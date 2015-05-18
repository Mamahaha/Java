package org.led.hellojpa.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.led.hellojpa.dao.GuildDao;
import org.led.hellojpa.entity.GuildEntity;
import org.led.hellojpa.utils.PersistenceContext;

public class GuildDaoImpl implements GuildDao {

	private EntityManager entityManager;
	
	public GuildDaoImpl(PersistenceContext context) {
		super();
		this.entityManager = context.getEntityManager();
	}
	
	public GuildEntity createGuildEntity(GuildEntity entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	public Long getIdByName(String name) {
		StringBuilder sb = new StringBuilder("SELECT a.id FROM GuildEntity a ");
        if (StringUtils.isNotBlank(name)) {
            sb.append(" WHERE a.name = :name");
        }
        Query query = entityManager.createQuery(sb.toString());
        if (StringUtils.isNotBlank(name)) {
            query.setParameter("name", name);
        }
        
        return Long.valueOf(query.getSingleResult().toString());        
	}

}
