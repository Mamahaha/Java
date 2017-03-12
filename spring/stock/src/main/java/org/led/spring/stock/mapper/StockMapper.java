package org.led.spring.stock.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.led.spring.stock.model.Stock;

public interface StockMapper {
	
	@Insert("INSERT INTO tb_stock(num, name, price) VALUES(#{num}, #{name}, #{price})")
	public int insertStock(@Param("num") String num, @Param("name") String name, @Param("price") float price);
	
	public Stock getStockById(@Param("num") String num);
	
	
}
