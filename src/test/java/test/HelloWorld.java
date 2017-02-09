package test;

import com.github.somefree.methodaroundinterceptor.annotation.Around;

@Around({ AdviceImpl1.class, AdviceImpl2.class })
public class HelloWorld {

	@Around(AdviceImpl1.class)
	public void hello1() {
		System.out.println("hello word...");
	}

	@Around(AdviceImpl2.class)
	public void hello2() {
		System.out.println("HELLO WORD...");
	}

	public void hello3() {
		System.out.println("hello " + i + s + "...");
	}

	String s;

	int i;

	public HelloWorld() {
	}

	public HelloWorld(String s) {
		this.s = s;
	}

	public HelloWorld(String s, int i) {
		this.s = s;
		this.i = i;
	}

}
