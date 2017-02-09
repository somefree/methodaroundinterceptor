package com.github.somefree.methodaroundinterceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.somefree.methodaroundinterceptor.advice.IAdvice;

/**
 * 作用于目标类 或者 目标方法的注解, 方法注解 会覆盖 类的注解, 注解中必须注入通知接口 @IAdvice 的实现类的class
 * 
 * @author dai.hongjiao
 *
 */
@Documented
@Inherited
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Around {

	Class<? extends IAdvice>[] value();

}
