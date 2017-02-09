package test.annotation;

import org.junit.Test;

import com.github.somefree.methodaroundinterceptor.ProxyFactory;

import test.HelloWorld;

public class AnnotationInterceptorTest {

	@Test
	public void test() throws Exception {
		HelloWorld bean = ProxyFactory.getSingletonProxy(HelloWorld.class);
		bean.hello1();
		System.out.println();
		System.out.println();
		bean.hello2();
		System.out.println();
		System.out.println();
		bean.hello3();
	}

	@Test
	public void test1() throws Exception {
		HelloWorld bean = ProxyFactory.getSingletonProxy(HelloWorld.class, new Class<?>[] { String.class }, new Object[] { "SB" });
		bean.hello1();
		System.out.println();
		System.out.println();
		bean.hello2();
		System.out.println();
		System.out.println();
		bean.hello3();
	}

	@Test
	public void test2() throws Exception {
		HelloWorld bean = ProxyFactory.getSingletonProxy(HelloWorld.class, new Class<?>[] { String.class, int.class }, new Object[] { "SB", 99 });
		bean.hello1();
		System.out.println();
		System.out.println();
		bean.hello2();
		System.out.println();
		System.out.println();
		bean.hello3();
	}

}
