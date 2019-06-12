package javaconvention;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ForEachAddRemoveConvertion {

	@Test
	public void remove1() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		for (final String s : list) {
			if ("2".equals(s)) {
				list.remove(s);
			}
		}
		
		//java.util.ConcurrentModificationException occur
		for (final String s : list) {
			System.out.println(s);
		}
	}
	
	@Test
	public void remove2() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		for (final String s : list) {
			if ("1".equals(s)) {
				list.remove(s);
			}
		}
		
		for (final String s : list) {
			System.out.println(s);
		}
	}
}
