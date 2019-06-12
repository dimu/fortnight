package generics;

import java.lang.reflect.*;
import java.util.*;

/**
 * 利用java.lang.reflect.Array类来动态生成类
 * @author dwx
 * 2016年12月21日
 *
 * @param <T>
 */
public class ArrayMaker<T> {
	
	//类型
	private Class<T> kind;

	public ArrayMaker(Class<T> kind) {
		this.kind = kind;
	}

	@SuppressWarnings("unchecked")
	T[] create(int size) {
		return (T[]) Array.newInstance(kind, size);
	}

	public static void main(String[] args) {
		ArrayMaker<String> stringMaker = new ArrayMaker<String>(String.class);
		System.out.println(Math.class.getSimpleName());
		System.out.println(Math.class.getName());
		String[] stringArray = stringMaker.create(9);
		System.out.println(Arrays.toString(stringArray));
	}
} 