package org.led.hellojpa.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerUtil {
	private static final String DB_IP = "127.0.0.1";
	private static final long DP_PORT = 5432;
	private static final String DB_NAME = "jpa";
	private static final String DB_USER = "led";
	private static final String DB_PASSWORD = "bmc";
	
	private EntityManagerUtil() {}
	
	private static EntityManagerFactory entityManagerFactory;
	
	private static Map<String, String> getDbPropsForPostgres() {

        Map<String, String> dbProps = new HashMap<String, String>();

        String dbUrl = "jdbc:postgresql://" + DB_IP + ":" + DP_PORT + "/" + DB_NAME;

        // set database properties
        dbProps.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        dbProps.put("javax.persistence.jdbc.user", DB_USER);
        dbProps.put("javax.persistence.jdbc.password", DB_PASSWORD);
        dbProps.put("javax.persistence.jdbc.url", dbUrl);
        dbProps.put("openjpa.jdbc.DBDictionary", "postgres");

        return dbProps;
    }
	
	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(DB_NAME, getDbPropsForPostgres());
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
}
