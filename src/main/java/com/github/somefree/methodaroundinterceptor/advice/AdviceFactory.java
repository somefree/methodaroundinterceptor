package com.github.somefree.methodaroundinterceptor.advice;

import java.util.HashMap;
import java.util.Map;

public class AdviceFactory {

	private static Map<String, IAdvice> adviceFactory;

	public static IAdvice getAdvice(Class<? extends IAdvice> clz) throws Exception {
		if (null == adviceFactory) {
			synchronized (AdviceFactory.class) {
				if (null == adviceFactory) {
					adviceFactory = new HashMap<String, IAdvice>();
				}
			}
		}
		IAdvice obj = adviceFactory.get(clz.getName());
		if (null == obj) {
			synchronized (adviceFactory) {
				obj = adviceFactory.get(clz.getName());
				if (null == obj) {
					obj = clz.newInstance();
					adviceFactory.put(clz.getName(), obj);
				}
			}
		}
		return obj;
	}

	public static void destory() {
		adviceFactory.clear();
		adviceFactory = null;
	}

}
