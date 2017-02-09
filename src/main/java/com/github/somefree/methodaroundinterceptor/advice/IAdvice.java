package com.github.somefree.methodaroundinterceptor.advice;

/**
 * 当使用注解拦截方法时, 该接口的实现类使用单例工厂构造对象
 * 
 * 此时建议该接口的实现类不要使用成员变量, 且一定要公开无参的构造方法
 * 
 * 否则, 建议通过方法名配置的方式获取代理类进行拦截
 * 
 * @author dai.hongjiao
 *
 */
public interface IAdvice {

	/**
	 * 方法执行前
	 * 
	 * @param className 原类的全名
	 * @param methodName 原方法名
	 * @param args 原方法执行参数
	 * @return 修改后的方法执行参数
	 */
	Object[] beforeInvoke(String className, String methodName, Object[] args);

	/**
	 * 方法返回前
	 * 
	 * @param className 原类的全名
	 * @param methodName 原方法名
	 * @param args 原方法执行参数
	 * @param result 原方法执行结果
	 * @param startIntercept 进入拦截的时刻(ms)
	 * @param startInvoke 原方法开始执行的时刻(ms)
	 * @return 修改后的方法执行结果
	 */
	Object beforeReturn(String className, String methodName, Object[] args, Object result, long startIntercept, long startInvoke);

}
