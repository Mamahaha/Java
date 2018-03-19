package org.led.spring.springboot_11_3.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAnnotation {
    String description() default "Change the price accuracy to 2 after point.";
}
