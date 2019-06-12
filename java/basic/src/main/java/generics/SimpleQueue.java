package generics;

// A different kind of container that is Iterable
import java.util.*;

/**
 * 使用linklist实现简单队列
 * @author dwx
 * 2016年12月21日
 *
 * @param <T>
 */
public class SimpleQueue<T> implements Iterable<T> {
	
	private LinkedList<T> storage = new LinkedList<T>();

	public void add(T t) {
		storage.offer(t);
	}

	public T get() {
		return storage.poll();
	}

	public Iterator<T> iterator() {
		return storage.iterator();
	}
}
