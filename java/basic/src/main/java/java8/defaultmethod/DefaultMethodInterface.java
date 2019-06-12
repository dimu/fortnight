package java8.defaultmethod;

/**
 * description:
 *
 * @version 2017年9月6日 上午10:05:36
 * @see
 * modify content------------author------------date
 */
public interface DefaultMethodInterface {
	
	/**
	 * description: 定义的正常消息接口
	 * @param msg 输出的消息
	 * @time: 2017年5月4日 下午3:21:50
	 */
	void sayMessage(String msg);
	
	/**
	 * description: 默认函数（defender函数）
	 * @param msg 输出的异常消息
	 * @time: 2017年5月4日 下午3:21:12
	 */
	default void sayError(String msg) {
		System.out.println("error msg :" + msg);
	}
}
