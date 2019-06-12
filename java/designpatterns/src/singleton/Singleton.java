package singleton;

/**
 * description: 方式一
 *
 * @version 2017年6月15日 下午2:48:35
 * @see
 * modify content------------author------------date
 */
public class Singleton {
	private static Singleton instance = new Singleton();
	
	/**
	 * 构造函数私有避免实例化对象
	 * Singleton.java constructor
	 */
	private Singleton(){
		
	}

	//这里提供了一个供外部访问本class的静态方法，可以直接访问　　
	public static Singleton getInstance() {
		return instance;
	}
} 

/**
 * description: 方式二，存在double-checked locking
 *
 * @version 2017年6月15日 下午2:48:48
 * @see
 * modify content------------author------------date
 */
class Singleton1 {
	
	private static Singleton1 instance;
	
	public static synchronized Singleton1 getInstance( ) {
		if (null ==  instance) {
			instance = new Singleton1();
		}
		
		return instance;
	}
}

	 
