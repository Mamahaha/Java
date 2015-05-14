package org.led.hellojpa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.led.hellojpa.entity.HelloJpa;
import org.led.hellojpa.utils.PersistenceContext;
import org.led.hellojpa.dao.HelloJpaDao;


public class HelloJpaDaoImpl implements HelloJpaDao{
	
	private EntityManager entityManager;
	
	public HelloJpaDaoImpl(PersistenceContext context) {
		super();
		this.entityManager = context.getEntityManager();
	}

	public HelloJpa createHelloJpa(HelloJpa hellpJpa) {
		entityManager.persist(hellpJpa);
		entityManager.flush();
		return hellpJpa;
	}

	public HelloJpa getHelloJpaByName(String name) {
		return entityManager.find(HelloJpa.class, name);
	}

	@SuppressWarnings("unchecked")
	public List<String> getHelloJpaNames() {
		StringBuilder sb = new StringBuilder(
                "select name from test_jpa");
        Query query = entityManager.createNativeQuery(sb.toString());
        
        return (List<String>) (query.getResultList());
	}
}

