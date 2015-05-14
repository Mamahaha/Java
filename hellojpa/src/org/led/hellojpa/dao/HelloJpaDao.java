package org.led.hellojpa.dao;

import java.util.List;

import org.led.hellojpa.entity.HelloJpa;


public interface HelloJpaDao {

	HelloJpa createHelloJpa(HelloJpa hellpJpa);
	HelloJpa getHelloJpaByName(String name);
	List<String> getHelloJpaNames();
	
}
