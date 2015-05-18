package org.led.hellojpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.led.hellojpa.dao.GuildDao;
import org.led.hellojpa.dao.impl.GuildDaoImpl;
import org.led.hellojpa.entity.GuildEntity;
import org.led.hellojpa.utils.EntityManagerUtil;
import org.led.hellojpa.utils.PersistenceContext;


public class MainApp {
	
	public static void addGuild() {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		EntityTransaction et = ctx.getEntityManager().getTransaction();
		et.begin();
		
		GuildDao dao = new GuildDaoImpl(ctx);
		
		GuildEntity entity = new GuildEntity();
		entity.setName("GOD");
		entity.setCount(10);
		entity.setLevel(3);
		entity.setFlag("GOD Flag");
		
		dao.createGuildDao(entity);
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
	
	public static void addPlayer() {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		EntityTransaction et = ctx.getEntityManager().getTransaction();
		et.begin();
		
		GuildDao dao = new GuildDaoImpl(ctx);
		
		GuildEntity entity = new GuildEntity();
		entity.setName("GOD");
		entity.setCount(10);
		entity.setLevel(3);
		entity.setFlag("GOD Flag");
		
		dao.createGuildDao(entity);
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
	public static void main(String[] args) {
		addGuild();
		//addPlayer();
	}
}
