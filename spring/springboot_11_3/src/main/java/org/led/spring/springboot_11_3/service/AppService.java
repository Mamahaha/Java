package org.led.spring.springboot_11_3.service;

import org.led.spring.springboot_11_3.annotation.ServiceAnnotation;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private double price = 4.5678;

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
}
