package org.led.spring.stock.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.led.spring.stock.model.Stock;

public interface StockMapper {
	
	@Insert("INSERT INTO tb_stock(id, name, price) VALUES(#{id}, #{name}, #{price})")
	public int insertStock(@Param("id") String id, @Param("name") String name, @Param("price") float price);
	
	public Stock getStockById(@Param("id") String id);
	
	
}
