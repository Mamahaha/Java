package org.led.hellojpa.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.led.hellojpa.dao.PlayerDao;
import org.led.hellojpa.entity.GuildEntity;
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
	
	public GuildEntity getGuildByPlayerName (String name) {
		StringBuilder sb = new StringBuilder("SELECT a.id FROM GuildEntity a ");
        if (StringUtils.isNotBlank(name)) {
            sb.append(" WHERE a.name = :name");
        }
        Query query = entityManager.createQuery(sb.toString());
        if (StringUtils.isNotBlank(name)) {
            query.setParameter("name", name);
        }
        
        return (GuildEntity)(query.getSingleResult());        
		
	}

}
