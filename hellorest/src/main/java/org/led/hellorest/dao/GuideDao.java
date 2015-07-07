package org.led.hellorest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.led.hellorest.model.guide.Guide;

public class GuideDao {
	private Map<String, Guide> guides = new ConcurrentHashMap<String, Guide>();
	
	public GuideDao() {
		Guide guide = new Guide();
		guide.setCount(4);
		guide.setDescription("4th guide");
		guide.setName("n4");
		guide.setLevel(44);
		guides.put(guide.getName(), guide);
	}
	
	public synchronized void addGuide(Guide guide) {
		guides.put(guide.getName(), guide);
	}
	
	public synchronized Guide getGuidebyName(String name) {
		return guides.get(name);
	}
	
	public synchronized void updateGuide(Guide guide) {
		guides.remove(guide.getName());
		guides.put(guide.getName(), guide);
	}
	
	public synchronized void deleteGuide(String name) {
		guides.remove(name);
	}
	
	public synchronized List<Guide> getAllGuides() {
		List<Guide> list = new ArrayList<Guide>();
		for(Map.Entry<String, Guide> s : guides.entrySet()) {
			list.add(s.getValue());
		}
		return list;
	}
}
