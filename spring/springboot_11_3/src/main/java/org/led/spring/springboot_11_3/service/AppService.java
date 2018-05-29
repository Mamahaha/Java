package org.led.spring.springboot_11_3.service;

import org.led.spring.springboot_11_3.annotation.ServiceAnnotation;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private double price = 4.5678;
    private Double[] prices;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        System.out.println("AppService setPrice: " + price);
        this.price = price;
    }
    public void setPrice2(double price) {
        System.out.println("AppService setPrice2: " + price);
        this.price = price;
    }

    @ServiceAnnotation
    public void setPrice3(double price) {
        System.out.println("AppService setPrice2: " + price);
        this.price = price;
    }

    @ServiceAnnotation
    public void setPrice4(double price) {
        System.out.println("AppService setPrice2: " + price);
        this.price = price;
    }

    public Double[] getPrices() {
        return prices;
    }

    public void setPrices(double[] prices) {
        this.prices = new Double[prices.length];
        for(int i = 0 ; i < prices.length; i++) {
            this.prices[i] = (double) ((int) ((double)(prices[i]) * 100)) / 100;
        }
    }
    public void printPrices() {
        for (Double d : prices) {
            System.out.println("prices: " + d);
        }
    }
}
