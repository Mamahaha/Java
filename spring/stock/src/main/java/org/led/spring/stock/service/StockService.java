package org.led.spring.stock.service;

import org.led.spring.stock.dao.StockDao;
import org.led.spring.stock.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
	
	@Autowired
	private StockDao stockDao;
	
	public int insertStock(String id, String name, float price) {
		return stockDao.insertStock(id, name, price);
	}
	
	public float getPriceById(String id) {
		return stockDao.getPriceById(id);		
	}

	public Stock getStockbyId(String id) {
	    return stockDao.getStockById(id);
	}
}
