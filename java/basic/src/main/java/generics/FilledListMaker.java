package generics;

import java.util.*;

/**
 * 
 * @author dwx
 * 2016年12月21日
 *
 * @param <T> 
 */
public class FilledListMaker<T> {
	
	//创建list
	List<T> create(T t, int n) {
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < n; i++) {
			result.add(t);
		}
		return result;
	}

	public static void main(String[] args) {
		FilledListMaker<String> stringMaker = new FilledListMaker<String>();
		List<String> list = stringMaker.create("Hello", 4);
		System.out.println(list);
		List<Double> list1 = new FilledListMaker<Double>().create(Math.random(), 4);
		System.out.println(list1);;
	}
} 