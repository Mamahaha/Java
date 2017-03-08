package org.led.spring.stock.dao;

import org.led.spring.stock.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockDao {

	@Autowired
	private StockMapper stockMapper;
	
	public int insertStock(String id, String name, float price) {
		return stockMapper.insertStock(id, name, price);
	}
	
	public float getPriceById(String id) {
		return stockMapper.getStockById(id).getPrice();		
	}
	
}
