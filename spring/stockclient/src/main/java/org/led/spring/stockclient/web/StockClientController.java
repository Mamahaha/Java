package org.led.spring.stockclient.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/stockclient")
@Api("Stock Client Operation API")
public class StockClientController {

	@Autowired
	private OkHttpClient okHttpClient;
	
	@Autowired
	private Environment env;
	
	@RequestMapping(value="/getpricebyid", method=RequestMethod.GET)
	public String getPriceById(@RequestParam("id") String id) {
        String url = String.format(env.getProperty("service.stock.url"));
        Response response = null;
        try {
            Request request = new Request.Builder().addHeader("id", id).url(url).build();
            response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            System.out.println("testokhttp成功，result: " + result);
            return result;
        } catch (IOException e) {
        	System.out.println("testokhttp失败，url: " + url);
            e.printStackTrace();
        } finally {
            if(response.body()!=null){
                try {
                    response.body().close();//一定要关闭，不然会泄露资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
