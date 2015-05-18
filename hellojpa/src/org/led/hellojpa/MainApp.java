package org.led.hellojpa;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.led.hellojpa.dao.GuildDao;
import org.led.hellojpa.dao.PlayerDao;
import org.led.hellojpa.dao.impl.GuildDaoImpl;
import org.led.hellojpa.dao.impl.PlayerDaoImpl;
import org.led.hellojpa.entity.GuildEntity;
import org.led.hellojpa.entity.PlayerEntity;
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
		entity.setName("DEVIL");
		entity.setCount(6);
		entity.setLevel(8);
		entity.setFlag("DEVIL Flag");
		
		dao.createGuildEntity(entity);
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
		
		PlayerDao pDao = new PlayerDaoImpl(ctx);
		GuildDao gDao = new GuildDaoImpl(ctx);
		
		PlayerEntity entity = new PlayerEntity();
		entity.setName("Moon");
		entity.setAge(15);
		entity.setLevel(18);
		entity.setScore(378960);
		entity.setSex("Male");
		entity.setGid(gDao.getIdByName("DEVIL"));
		
		pDao.createPlayerEntity(entity);
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
	
	public static void testSqlCmd(String sqlCmd) {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		Query query = ctx.getEntityManager().createQuery(sqlCmd);
		@SuppressWarnings("unchecked")
		List<GuildEntity> list = (List<GuildEntity>)query.getResultList();
		for (GuildEntity entity : list) {
			System.out.println(entity.toString());
		}
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
	public static void main(String[] args) {
		//addGuild();
		addPlayer();
		
		//==============
		//testSqlCmd("SELECT a FROM GuildEntity a");
	}
}
