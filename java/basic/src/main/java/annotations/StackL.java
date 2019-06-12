package annotations;

import java.util.*;

/**
 * 
 * @author dwx
 * 2016年12月28日
 *
 * @param <T>
 */
public class StackL<T> {
	
	private LinkedList<T> list = new LinkedList<T>();

	public void push(T v) {
		list.addFirst(v);
	}

	public T top() {
		return list.getFirst();
	}

	public T pop() {
		return list.removeFirst();
	}
	
} /// :~
