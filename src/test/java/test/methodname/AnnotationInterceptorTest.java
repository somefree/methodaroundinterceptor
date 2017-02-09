package test.methodname;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.somefree.methodaroundinterceptor.ProxyFactory;
import com.github.somefree.methodaroundinterceptor.advice.IAdvice;

import test.AdviceImpl1;
import test.AdviceImpl2;
import test.HelloWorld;

public class AnnotationInterceptorTest {

	@Test
	public void test() throws Exception {
		Map<String, List<IAdvice>> map = new HashMap<String, List<IAdvice>>();

		List<IAdvice> list1 = new ArrayList<IAdvice>();
		list1.add(new AdviceImpl1());

		List<IAdvice> list2 = new ArrayList<IAdvice>();
		list2.add(new AdviceImpl2());

		List<IAdvice> list3 = new ArrayList<IAdvice>();
		list3.add(new AdviceImpl1());
		list3.add(new AdviceImpl2());

		map.put("hello1", list1);
		map.put("hello2", list2);
		map.put("hello3", list3);

		HelloWorld bean = ProxyFactory.getSingletonProxy(HelloWorld.class, map);
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
		Map<String, List<IAdvice>> map = new HashMap<String, List<IAdvice>>();

		List<IAdvice> list1 = new ArrayList<IAdvice>();
		list1.add(new AdviceImpl1());

		List<IAdvice> list2 = new ArrayList<IAdvice>();
		list2.add(new AdviceImpl2());

		List<IAdvice> list3 = new ArrayList<IAdvice>();
		list3.add(new AdviceImpl1());
		list3.add(new AdviceImpl2());

		map.put("hello1", list1);
		map.put("hello2", list2);
		map.put("hello3", list3);

		HelloWorld bean = ProxyFactory.getSingletonProxy(HelloWorld.class, new Class<?>[] { String.class }, new Object[] { "SB" }, map);
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
		Map<String, List<IAdvice>> map = new HashMap<String, List<IAdvice>>();

		List<IAdvice> list1 = new ArrayList<IAdvice>();
		list1.add(new AdviceImpl1());

		List<IAdvice> list2 = new ArrayList<IAdvice>();
		list2.add(new AdviceImpl2());

		List<IAdvice> list3 = new ArrayList<IAdvice>();
		list3.add(new AdviceImpl1());
		list3.add(new AdviceImpl2());

		map.put("hello1", list1);
		map.put("hello2", list2);
		map.put("hello3", list3);

		HelloWorld bean = ProxyFactory.getSingletonProxy(HelloWorld.class, new Class<?>[] { String.class, int.class }, new Object[] { "SB", 99 }, map);
		bean.hello1();
		System.out.println();
		System.out.println();
		bean.hello2();
		System.out.println();
		System.out.println();
		bean.hello3();
	}

}
