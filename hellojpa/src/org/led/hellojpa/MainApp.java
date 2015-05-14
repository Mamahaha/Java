package org.led.hellojpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.led.hellojpa.dao.HelloJpaDao;
import org.led.hellojpa.dao.impl.HelloJpaDaoImpl;
import org.led.hellojpa.entity.HelloJpa;
import org.led.hellojpa.utils.EntityManagerUtil;
import org.led.hellojpa.utils.PersistenceContext;


public class MainApp {
	public static void main(String[] args) {
		PersistenceContext ctx = new PersistenceContext();
		EntityManagerFactory factory = EntityManagerUtil
                .getEntityManagerFactory();
		
		ctx.setEntityManager(factory.createEntityManager());
		EntityTransaction et = ctx.getEntityManager().getTransaction();
		et.begin();
		
		HelloJpaDao hjd = new HelloJpaDaoImpl(ctx);
		
		HelloJpa testJpa = new HelloJpa();
		testJpa.setDate(12348);
		//testJpa.setName("aaa");
		hjd.createHelloJpa(testJpa);
		et.commit();
		
		if (ctx != null) {
			ctx.getEntityManager().close();
        }
	}
}
