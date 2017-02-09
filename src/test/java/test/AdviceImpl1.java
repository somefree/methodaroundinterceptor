package test;

import com.github.somefree.methodaroundinterceptor.advice.IAdvice;

public class AdviceImpl1 implements IAdvice {

	@Override
	public Object[] beforeInvoke(String className, String methodName, Object[] args) {
		System.out.println("11111111111111");
		return args;
	}

	@Override
	public Object beforeReturn(String className, String methodName, Object[] args, Object result, long startIntercept, long startInvoke) {
		System.out.println("2222222222222");
		return result;
	}

}
