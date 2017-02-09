package com.github.somefree.methodaroundinterceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.somefree.methodaroundinterceptor.advice.AdviceFactory;
import com.github.somefree.methodaroundinterceptor.advice.IAdvice;
import com.github.somefree.methodaroundinterceptor.interceptor.AnnotationInterceptor;
import com.github.somefree.methodaroundinterceptor.interceptor.IMethodAroundInterceptor;
import com.github.somefree.methodaroundinterceptor.interceptor.MethodNameInterceptor;

import net.sf.cglib.proxy.Enhancer;

@SuppressWarnings("unchecked")
public final class ProxyFactory {

	private static Map<String, Object> singletonContext;

	private static IMethodAroundInterceptor annotationInterceptor;

	/**
	 * 通过无参的构造方法, 获取代理类, 单例, 拦截通知由注解 @Around 配置
	 * 
	 * @param clz 目标类的class
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getSingletonProxy(Class<T> clz) {
		initSingletonContext();
		initAnnotationInterceptor();
		Object obj = singletonContext.get(clz.getName());
		if (null == obj) {
			synchronized (singletonContext) {
				obj = singletonContext.get(clz.getName());
				if (null == obj) {
					obj = Enhancer.create(clz, annotationInterceptor);
					singletonContext.put(clz.getName(), obj);
				}
			}
		}
		return (T) obj;
	}

	/**
	 * 通过无参的构造方法, 获取代理类, 单例, 拦截通知由 参数methodNameAndAdvices 配置
	 * 
	 * @param clz 目标类的class
	 * @param methodNameAndAdvices 需要拦截的方法名和通知配置, key:目标类的方法名, value:通知集合, 有序
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getSingletonProxy(Class<T> clz, Map<String, List<IAdvice>> methodNameAndAdvices) {
		initSingletonContext();
		Object obj = singletonContext.get(clz.getName());
		if (null == obj) {
			synchronized (singletonContext) {
				obj = singletonContext.get(clz.getName());
				if (null == obj) {
					MethodNameInterceptor methodNameInterceptor = new MethodNameInterceptor(methodNameAndAdvices);
					obj = Enhancer.create(clz, methodNameInterceptor);
					singletonContext.put(clz.getName(), obj);
				}
			}
		}
		return (T) obj;
	}

	/**
	 * 通过指定的构造方法, 获取代理类, 单例, 拦截通知由注解 @Around 配置
	 * 
	 * @param clz 目标类的class
	 * @param argumentTypes 构造方法的参数class数组
	 * @param arguments 构造方法的参数内容数组
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getSingletonProxy(Class<T> clz, Class<?>[] argumentTypes, Object[] arguments) {
		initSingletonContext();
		initAnnotationInterceptor();
		Object obj = singletonContext.get(clz.getName() + Arrays.asList(arguments));
		if (null == obj) {
			synchronized (singletonContext) {
				obj = singletonContext.get(clz.getName() + Arrays.asList(arguments));
				if (null == obj) {
					Enhancer enhancer = new Enhancer();
					enhancer.setSuperclass(clz);
					enhancer.setCallback(annotationInterceptor);
					obj = enhancer.create(argumentTypes, arguments);
					singletonContext.put(clz.getName() + Arrays.asList(arguments), obj);
				}
			}
		}
		return (T) obj;
	}

	/**
	 * 通过指定的构造方法, 获取代理类, 单例, 拦截通知由 参数methodNameAndAdvices 配置
	 * 
	 * @param clz 目标类的class
	 * @param argumentTypes 构造方法的参数class数组
	 * @param arguments 构造方法的参数内容数组
	 * @param methodNameAndAdvices 需要拦截的方法名和通知配置, key:目标类的方法名, value:通知集合, 有序
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getSingletonProxy(Class<T> clz, Class<?>[] argumentTypes, Object[] arguments,
			Map<String, List<IAdvice>> methodNameAndAdvices) {
		initSingletonContext();
		Object obj = singletonContext.get(clz.getName() + Arrays.asList(arguments));
		if (null == obj) {
			synchronized (singletonContext) {
				obj = singletonContext.get(clz.getName() + Arrays.asList(arguments));
				if (null == obj) {
					Enhancer enhancer = new Enhancer();
					enhancer.setSuperclass(clz);
					MethodNameInterceptor methodNameInterceptor = new MethodNameInterceptor(methodNameAndAdvices);
					enhancer.setCallback(methodNameInterceptor);
					obj = enhancer.create(argumentTypes, arguments);
					singletonContext.put(clz.getName() + Arrays.asList(arguments), obj);
				}
			}
		}
		return (T) obj;
	}

	/**
	 * 通过无参的构造方法, 获取代理类, 多例, 拦截通知由注解 @Around 配置
	 * 
	 * @param clz 目标类的class
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getPrototypeProxy(Class<T> clz) {
		initAnnotationInterceptor();
		return (T) Enhancer.create(clz, annotationInterceptor);
	}

	/**
	 * 通过无参的构造方法, 获取代理类, 多例, 拦截通知由 参数methodNameAndAdvices 配置
	 * 
	 * @param clz 目标类的class
	 * @param methodNameAndAdvices 需要拦截的方法名和通知配置, key:目标类的方法名, value:通知集合, 有序
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getPrototypeProxy(Class<T> clz, Map<String, List<IAdvice>> methodNameAndAdvices) {
		return (T) Enhancer.create(clz, new MethodNameInterceptor(methodNameAndAdvices));
	}

	/**
	 * 通过指定的构造方法, 获取代理类, 单例, 拦截通知由注解 @Around 配置
	 * 
	 * @param clz 目标类的class
	 * @param argumentTypes 构造方法的参数class数组
	 * @param arguments 构造方法的参数内容数组
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getPrototypeProxy(Class<T> clz, Class<?>[] argumentTypes, Object[] arguments) {
		initAnnotationInterceptor();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clz);
		enhancer.setCallback(annotationInterceptor);
		Object obj = enhancer.create(argumentTypes, arguments);
		return (T) obj;
	}

	/**
	 * 通过指定的构造方法, 获取代理类, 单例, 拦截通知由 参数methodNameAndAdvices 配置
	 * 
	 * @param clz 目标类的class
	 * @param argumentTypes 构造方法的参数class数组
	 * @param arguments 构造方法的参数内容数组
	 * @param methodNameAndAdvices 需要拦截的方法名和通知配置, key:目标类的方法名, value:通知集合, 有序
	 * @return 代理类对象, 即目标类的子类对象
	 */
	public static <T> T getPrototypeProxy(Class<T> clz, Class<?>[] argumentTypes, Object[] arguments,
			Map<String, List<IAdvice>> methodNameAndAdvices) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clz);
		enhancer.setCallback(new MethodNameInterceptor(methodNameAndAdvices));
		Object obj = enhancer.create(argumentTypes, arguments);
		return (T) obj;
	}

	private static void initSingletonContext() {
		if (null == singletonContext) {
			synchronized (ProxyFactory.class) {
				if (null == singletonContext) {
					singletonContext = new HashMap<String, Object>();
				}
			}
		}
	}

	private static void initAnnotationInterceptor() {
		if (null == annotationInterceptor) {
			synchronized (ProxyFactory.class) {
				if (null == annotationInterceptor) {
					annotationInterceptor = new AnnotationInterceptor();
				}
			}
		}
	}

	public static void destory() {
		AdviceFactory.destory();
		singletonContext.clear();
		singletonContext = null;
		annotationInterceptor = null;
	}

}
