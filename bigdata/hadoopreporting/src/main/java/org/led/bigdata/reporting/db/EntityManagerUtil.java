package org.led.bigdata.reporting.db;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class EntityManagerUtil {
	private static String driver = "org.postgresql.Driver";
	private static String dictionary = "postgres";
	private static String ip = "127.0.0.1";
	private static long port = 5432;
	private static String ssl = "false";
	private static String name = "jpa";
	private static String user = "led";
	private static String password = "bmc";
	private static String urlPrefix = "jdbc:postgresql";
	
	private EntityManagerUtil() {}
	
	private static EntityManagerFactory entityManagerFactory;
	
	private static Map<String, String> getDbPropsForPostgres() {
		driver = (ConfigLoader.getValue("jdbc_driver") != null) ? ConfigLoader.getValue("jdbc_driver") : driver;
		dictionary = (ConfigLoader.getValue("db_dictionary") != null) ? ConfigLoader.getValue("db_dictionary") : dictionary;
		ip = (ConfigLoader.getValue("ip") != null) ? ConfigLoader.getValue("ip") : ip;
		port = (ConfigLoader.getValue("port") != null) ? Integer.parseInt(ConfigLoader.getValue("port")) : port;
		ssl = (ConfigLoader.getValue("ssl") != null) ? ConfigLoader.getValue("ssl") : ssl;
		name = (ConfigLoader.getValue("name") != null) ? ConfigLoader.getValue("name") : name;
		user = (ConfigLoader.getValue("user") != null) ? ConfigLoader.getValue("user") : user;
		password = (ConfigLoader.getValue("password") != null) ? ConfigLoader.getValue("password") : password;
		urlPrefix = (ConfigLoader.getValue("url_prefix") != null) ? ConfigLoader.getValue("url_prefix") : urlPrefix;
		
        Map<String, String> dbProps = new HashMap<String, String>();
        String dbUrl = urlPrefix + "://" + ip + ":" + port + "/" + name;

        // set database properties
        dbProps.put("javax.persistence.jdbc.driver", driver);
        dbProps.put("javax.persistence.jdbc.user", user);
        dbProps.put("javax.persistence.jdbc.password", password);
        dbProps.put("javax.persistence.jdbc.url", dbUrl);
        dbProps.put("openjpa.jdbc.DBDictionary", dictionary);
        if (ssl.equals("true")) {
        	dbProps.put("javax.persistence.jdbc.ssl", ssl);
        }

        return dbProps;
    }
	
	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {			
			Map<String, String> props = getDbPropsForPostgres();
			entityManagerFactory = Persistence.createEntityManagerFactory(name, props);
		}
		return entityManagerFactory;
	}
	
	public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void closeEntityManager() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
    
    
    //==================
    public static void addReportingRecord(ReportingEntity entity) {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		EntityTransaction et = ctx.getEntityManager().getTransaction();
		et.begin();
		ctx.getEntityManager().persist(entity);
		ctx.getEntityManager().flush();
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
    
    public static void addTypeSummaryRecord(ReportingTypeSummaryEntity entity) {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		EntityTransaction et = ctx.getEntityManager().getTransaction();
		et.begin();
		ctx.getEntityManager().persist(entity);
		ctx.getEntityManager().flush();
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
    
    public static void main(String[] args) {
    	ConfigLoader.initContent("/home/led/tmp/db.conf");
    	
    	ReportingEntity entity = new ReportingEntity();
		entity.setName(String.valueOf(0xF0000213 + 1));
		entity.setType("iphone");
		entity.setCount(6);
		entity.setArriveTime(123456677);
		entity.setRequestURI("http://10.175.183.125:80/reportingService/test_bdc/F00002130184_0_20150902013200000_16777221");
		entity.setSourceAddress("http://10.175.183.121/10M.mp4");
		
    	addReportingRecord(entity);
    }
}

