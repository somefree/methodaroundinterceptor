package com.github.somefree.methodaroundinterceptor.interceptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.somefree.methodaroundinterceptor.advice.IAdvice;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 方法拦截器
 * 
 * 通过方法名拦截
 * 
 * 获取注解中的 @IAdvice 的实现类的class
 * 数组中的顺序决定着执行顺序, 越靠前,越靠外(把目标方法当作中心, 前后包裹)
 * 
 * 通过无参的构造方法, 单例地创建 IAdvice 实现类
 * 
 * 有序地对目标方法进行 执行前拦截, 执行, 执行后拦截
 * 
 */
public class MethodNameInterceptor implements IMethodAroundInterceptor {

	private Map<String, List<IAdvice>> methodNameAndAdvices;

	public MethodNameInterceptor(Map<String, List<IAdvice>> methodNameAndAdvices) {
		this.methodNameAndAdvices = methodNameAndAdvices;
	}

	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {

		List<IAdvice> adviceList = null;

		if (methodNameAndAdvices != null) {
			adviceList = methodNameAndAdvices.get(method.getName());
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