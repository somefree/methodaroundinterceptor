package com.github.somefree.methodaroundinterceptor.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.somefree.methodaroundinterceptor.advice.AdviceFactory;
import com.github.somefree.methodaroundinterceptor.advice.IAdvice;
import com.github.somefree.methodaroundinterceptor.annotation.Around;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 注解拦截器
 * 
 * 获取代理类的 @Around 注解
 * 
 * 获取注解中的 @IAdvice 的实现类的class
 * 数组中的顺序决定着执行顺序, 越靠前,越靠外(把目标方法当作中心, 前后包裹)
 * 
 * 通过无参的构造方法, 单例地创建 IAdvice 实现类
 * 
 * 有序地对目标方法进行 执行前拦截, 执行, 执行后拦截
 * 
 */
public class AnnotationInterceptor implements IMethodAroundInterceptor {

	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {

		List<IAdvice> adviceList = null;

		Around around = target.getClass().getAnnotation(Around.class);
		if (around != null) {
			adviceList = new ArrayList<IAdvice>(around.value().length);
			for (Class<? extends IAdvice> clz : around.value()) {
				adviceList.add(AdviceFactory.getAdvice(clz));
			}
		}

		around = method.getAnnotation(Around.class);
		if (around != null) {
			adviceList = new ArrayList<IAdvice>(around.value().length);// 方法附加的注解 覆盖 类附加的注解
			for (Class<? extends IAdvice> clz : around.value()) {
				adviceList.add(AdviceFactory.getAdvice(clz));
			}
		}

		if (adviceList == null || adviceList.isEmpty()) {// 没有注解, 不拦截
			return proxy.invokeSuper(target, args);
		}

		long startIntercept = System.currentTimeMillis();
		String clz = target.getClass().getName();
		String className = clz.substring(0, clz.indexOf("$"));
		String methodName = method.getName();

		for (IAdvice advice : adviceList) {
			args = advice.beforeInvoke(className, methodName, args);
		}

		long startInvoke = System.currentTimeMillis();
		Object result = proxy.invokeSuper(target, args);// 从设计上考虑, AOP不应该参与异常处理, 所以此处不catch异常

		Collections.reverse(adviceList);
		for (IAdvice advice : adviceList) {
			result = advice.beforeReturn(className, methodName, args, result, startIntercept, startInvoke);
		}

		return result;
	}

}
