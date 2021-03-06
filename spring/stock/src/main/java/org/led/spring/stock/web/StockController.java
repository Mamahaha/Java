package org.led.spring.stock.web;


import java.util.List;

import org.led.spring.stock.config.StockConfig;
import org.led.spring.stock.model.Stock;
import org.led.spring.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/stock")
@Api("Stock Operation API")
public class StockController {

    @Autowired
    private StockService stockService;
    
    @Autowired
    private StockConfig stockConfig;
    
    @ApiOperation("get price by stock id")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="header",name="id",dataType="String",required=true,value="stock id",defaultValue="000100"),
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/getpricebyid", method=RequestMethod.GET)
    public float getStockPrice(@RequestHeader("id") String id) {
        return stockService.getPriceById(id);
    }
    
    @RequestMapping(value="/insertstock", method=RequestMethod.POST)
    public int insertStock(@RequestHeader("id") String id, @RequestParam("name") String name, @RequestParam("price") float price) {
        return stockService.insertStock(id, name, price);
    }
    
    @RequestMapping(value="/loadstocks", method=RequestMethod.GET)
    public String loadStocks() {
        StringBuilder sb = new StringBuilder();
        List<String> idList = stockConfig.getIdList();
        
        for (String id : idList) {
            Stock stock = stockService.getStockbyId(id);
            if (stock != null) {
            	sb.append(id).append(" : ").append(stock.getName()).append(" : ").append(stock.getPrice()).append("\n");
            } else {
            	System.out.println("Could not find stock with id: " + id + ".");
            }
        }

        return sb.toString();
    }
}
