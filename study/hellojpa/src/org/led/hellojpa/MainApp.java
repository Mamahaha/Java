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
import org.led.hellojpa.utils.ConfigLoader;
import org.led.hellojpa.utils.EntityManagerUtil;
import org.led.hellojpa.utils.PersistenceContext;


public class MainApp<T> {
	
	public static void addGuild() {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		EntityTransaction et = ctx.getEntityManager().getTransaction();
		et.begin();
		
		GuildDao dao = new GuildDaoImpl(ctx);
		
		GuildEntity entity = new GuildEntity();
		entity.setName("DEVIL1");
		entity.setCount(6);
		entity.setLevel(8);
		entity.setFlag("DEVIL 1 Flag");
		
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
		entity.setName("Star1");
		entity.setAge(15);
		entity.setLevel(18);
		entity.setScore(278960);
		entity.setSex("Female");
		entity.setGuild(gDao.getGuildByName("DEVIL1"));
		
		pDao.createPlayerEntity(entity);
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
	
	
	public <T> void testSqlCmd(String sqlCmd) {
		PersistenceContext ctx = new PersistenceContext();
		
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		Query query = ctx.getEntityManager().createQuery(sqlCmd);
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>)query.getResultList();
		for (T item : list) {
			System.out.print(item.toString());
		}
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}

	public static void main(String[] args) {
		ConfigLoader.initContent("/tmp/db.conf");
		//String sqlCmd = ConfigLoader.getValue("sql_cmd");
		addGuild();
		//addPlayer();
		
		//========1======
		//MainApp<GuildEntity> app = new MainApp<GuildEntity>();
		//app.testSqlCmd("SELECT a FROM GuildEntity a");
		//========2======
//		MainApp<Long> app = new MainApp<Long>();
//		app.testSqlCmd("SELECT a.id FROM GuildEntity a");
		//========3======
		//MainApp<GuildEntity> app = new MainApp<GuildEntity>();
		//app.testSqlCmd(sqlCmd);
	}
}
