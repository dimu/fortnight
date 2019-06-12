package java8.defaultmethod;

import org.junit.Test;

/**
 * description: java8 default method
 *
 * @version 2017年5月4日 下午3:01:51
 * @see
 * modify content------------author------------date
 */
public class Java8DefaultMethod implements DefaultMethodInterface{

	@Override
	public void sayMessage(String msg) {
		System.out.println("normal message : " + msg);
	}

	@Test
	public void defaultMethodTest() {
		sayMessage("hello word");
		sayError("null pointer exception");
	}
}
