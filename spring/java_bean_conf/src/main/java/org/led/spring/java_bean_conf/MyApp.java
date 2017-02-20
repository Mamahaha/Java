package org.led.spring.java_bean_conf;

import org.led.spring.java_bean_conf.beans.Plane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MyApp 
{
	public static void main(String[] args) {  
        //ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/bean.xml");// 读取bean.xml中的内容  
        ApplicationContext annotationContext = new AnnotationConfigApplicationContext("org.led.spring.java_bean_conf.conf");  
        Plane p = annotationContext.getBean("airplane", Plane.class);
        System.out.println("band is: " + p.getBand());
        System.out.println("price is: " + p.getPrice());       
    } 
}
