package org.led.spring.stock.config;


import java.util.ArrayList;
import java.util.List;

import org.led.spring.stock.common.ApplicationProperties;

import lombok.Data;


@Data
@ApplicationProperties(locations = "classpath:config.yml", prefix = "stock")
public class StockConfig {

    private List<String> idList = new ArrayList<>();

	public List<String> getIdList() {
		System.out.println("===StockConfig get obj: " + this + "===list len: " + idList.size());
		return idList;
	}

	public void setIdList(List<String> idList) {
		System.out.println("===StockConfig set obj: " + this + "===list len: " + idList.size());
		this.idList = idList;
	}
}
