package generics;

import java.lang.reflect.*;
import java.util.*;
import static net.mindview.util.Print.*;

/**
 * 方法反射
 * @author dwx
 * 2016年12月21日
 *
 */
public class Apply {
	
	/**
	 * 类型声明，泛型声明<T, S extends Iterable<? extends T>>，其中S限定为Iterable的子类
	 * T限制T的子类
	 * @param seq 序列
	 * @param f 方法
	 * @param args 不定长参数
	 */
	public static <T, S extends Iterable<? extends T>> void apply(S seq, Method f, Object... args) {
		try {
			for (T t : seq) {
				f.invoke(t, args);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		List<Shape> shapes = new ArrayList<Shape>();
		for (int i = 0; i < 10; i++) {
			shapes.add(new Shape());
		}
		Apply.apply(shapes, Shape.class.getMethod("rotate"));
		//传参
		Apply.apply(shapes, Shape.class.getMethod("resize", int.class), 5);
		List<Square> squares = new ArrayList<Square>();
		for (int i = 0; i < 10; i++) {
			squares.add(new Square());
		}
		Apply.apply(squares, Shape.class.getMethod("rotate"));
		Apply.apply(squares, Shape.class.getMethod("resize", int.class), 5);

		Apply.apply(new FilledList<Shape>(Shape.class, 10), Shape.class.getMethod("rotate"));
		Apply.apply(new FilledList<Shape>(Square.class, 10), Shape.class.getMethod("rotate"));

		SimpleQueue<Shape> shapeQ = new SimpleQueue<Shape>();
		for (int i = 0; i < 5; i++) {
			shapeQ.add(new Shape());
			shapeQ.add(new Square());
		}
		Apply.apply(shapeQ, Shape.class.getMethod("rotate"));
	}
}

/**
 * 
 * @author dwx
 * 2016年12月21日
 *
 */
class Shape {
	public void rotate() {
		print(this + " rotate");
	}

	public void resize(int newSize) {
		print(this + " resize " + newSize);
	}
}

/**
 * 
 * @author dwx
 * 2016年12月21日
 *
 */
class Square extends Shape {
}

/**
 * arrayList子类
 * @author dwx
 * 2016年12月21日
 *
 * @param <T> 泛型参数
 */
class FilledList<T> extends ArrayList<T> {
	private static final long serialVersionUID = -7368420370171316290L;

	/**
	 * T的子类
	 * @param type 类型
	 * @param size 个数
	 */
	public FilledList(Class<? extends T> type, int size) {
		try {
			for (int i = 0; i < size; i++) {
				//type实例化
				add(type.newInstance());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
