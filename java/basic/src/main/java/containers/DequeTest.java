package containers;

//: containers/DequeTest.java
import net.mindview.util.*;
import static net.mindview.util.Print.*;

/**
 * description: 双向队列
 *
 * @version 2017年4月7日 上午10:47:48
 * @see
 * modify content------------author------------date
 */
public class DequeTest {
	
	static void fillTest(Deque<Integer> deque) {
		for (int i = 20; i < 27; i++) {
			deque.addFirst(i);
		}
			
		for (int i = 50; i < 55; i++) {
			deque.addLast(i);
		}
	}

	public static void main(String[] args) {
		//双向队列
		Deque<Integer> di = new Deque<Integer>();
		fillTest(di);
		print(di);
		while (di.size() != 0) {
			printnb(di.removeFirst() + " ");
		}
			
		print();
		fillTest(di);
		while (di.size() != 0) {
			printnb(di.removeLast() + " ");
		}
			
	}
} 