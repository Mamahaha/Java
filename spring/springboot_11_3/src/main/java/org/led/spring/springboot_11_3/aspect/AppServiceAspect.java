package org.led.spring.springboot_11_3.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppServiceAspect {
    private static final String setMethod12= "execution(* org.led.spring.springboot_11_3.service.AppService.setPrice(..)) ||" +
            "execution(* org.led.spring.springboot_11_3.service.AppService.setPrice2(..))";

    @Around(setMethod12)
    public void roundT12(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        System.out.println("price: " + args[0]);
        double realPrice = (double) ((int) ((double)(args[0]) * 100)) / 100;
        System.out.println("new price: " + realPrice);
        args[0] = realPrice;
        pjp.proceed(args);
    }

    //========================================================================
    @Pointcut("@annotation(org.led.spring.springboot_11_3.annotation.ServiceAnnotation)")
    public void priceAspect() {}

    @Around("priceAspect()")
    public void roundT34(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        System.out.println("price: " + args[0]);
        double realPrice = (double) ((int) ((double)(args[0]) * 100)) / 100;
        System.out.println("new price: " + realPrice);
        args[0] = realPrice;
        pjp.proceed(args);
    }
}
