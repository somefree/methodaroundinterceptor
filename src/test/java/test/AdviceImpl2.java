package test;

import com.github.somefree.methodaroundinterceptor.advice.IAdvice;

public class AdviceImpl2 implements IAdvice {

	@Override
	public Object[] beforeInvoke(String className, String methodName, Object[] args) {
		System.out.println("aaaaaaaaaaa");
		return args;
	}

	@Override
	public Object beforeReturn(String className, String methodName, Object[] args, Object result, long startIntercept, long startInvoke) {
		System.out.println("bbbbbbbbbbbbb");
		return result;
	}

}
